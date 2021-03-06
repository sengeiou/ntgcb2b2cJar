package com.aebiz.app.web.commons.shiro.realm;

import com.aebiz.app.sys.modules.models.Sys_role;
import com.aebiz.app.sys.modules.models.Sys_user;
import com.aebiz.app.sys.modules.services.SysRoleService;
import com.aebiz.app.sys.modules.services.SysUserService;
import com.aebiz.baseframework.shiro.exception.CaptchaEmptyException;
import com.aebiz.baseframework.shiro.exception.CaptchaIncorrectException;
import com.aebiz.app.web.commons.shiro.token.PlatformCaptchaToken;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.nutz.castor.Castors;
import org.nutz.dao.Cnd;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wizzer on 2017/1/11.
 */
public class PlatformAuthorizingRealm extends AuthorizingRealm {
    private static final Log log = Logs.get();
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token.getClass().isAssignableFrom(PlatformCaptchaToken.class)) {
            PlatformCaptchaToken authcToken = Castors.me().castTo(token, PlatformCaptchaToken.class);
            String loginname = authcToken.getUsername();
            String captcha = authcToken.getCaptcha();
            if (Strings.isBlank(loginname)) {
                throw Lang.makeThrow(AuthenticationException.class, "Account name is empty");
            }
            int errCount = NumberUtils.toInt(Strings.sNull(SecurityUtils.getSubject().getSession(true).getAttribute("platformErrCount")));
            log.debug("platformErrCount::" + errCount);
            if (errCount > 2) {
                //输错三次显示验证码窗口
                if (Strings.isBlank(captcha)) {
                    throw Lang.makeThrow(CaptchaEmptyException.class, "Captcha is empty");
                }
                String _captcha = Strings.sBlank(SecurityUtils.getSubject().getSession(true).getAttribute("platformCaptcha"));
                if (!authcToken.getCaptcha().equalsIgnoreCase(_captcha)) {
                    throw Lang.makeThrow(CaptchaIncorrectException.class, "Captcha is error");
                }
            }
            Sys_user user = sysUserService.fetch(Cnd.where("loginname", "=", loginname));
            if (Lang.isEmpty(user)) {
                throw Lang.makeThrow(UnknownAccountException.class, "Account [ %s ] not found", loginname);
            }
            if (user.isDisabled()) {
                throw Lang.makeThrow(LockedAccountException.class, "Account [ %s ] is locked.", loginname);
            }
            sysUserService.fetchLinks(user, null);
            sysUserService.fillMenu(user);
            SecurityUtils.getSubject().getSession(true).setAttribute("platformErrCount", 0);
            SecurityUtils.getSubject().getSession(true).setAttribute("uid", user.getId());
            SecurityUtils.getSubject().getSession(true).setAttribute("username", user.getUsername());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
            return info;
        }
        return null;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object object = principals.getPrimaryPrincipal();
        if (object.getClass().isAssignableFrom(Sys_user.class)) {
            Sys_user user = Castors.me().castTo(object, Sys_user.class);
            if (!Lang.isEmpty(user) && !user.isDisabled()) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                log.debug("sysUserService.getRoleCodeList(user):::" + Json.toJson(sysUserService.getRoleCodeList(user)));
                info.addRoles(sysUserService.getRoleCodeList(user));
                for (Sys_role role : user.getRoles()) {
                    if (!role.isDisabled())
                        info.addStringPermissions(sysRoleService.getPermissionNameList(role));
                }
                return info;
            } else {
                return null;
            }
        }
        return null;
    }

    public PlatformAuthorizingRealm() {
        this(null, null);
    }

    public PlatformAuthorizingRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
        setAuthenticationTokenClass(PlatformCaptchaToken.class);
    }

    public PlatformAuthorizingRealm(CacheManager cacheManager) {
        this(cacheManager, null);
    }

    public PlatformAuthorizingRealm(CredentialsMatcher matcher) {
        this(null, matcher);
    }
}