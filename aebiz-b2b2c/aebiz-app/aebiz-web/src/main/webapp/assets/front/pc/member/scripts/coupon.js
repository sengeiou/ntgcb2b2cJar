"use strict";$(".coupon-list").eq(0).show(),$(document).on("click",".coupon-tab li",function(){$(".coupon-list").hide().eq(num).fadeIn()}),$(document).on("click",".icon-delete1",function(){layer.open({type:1,content:$("#coupon-delete"),area:"560px",title:["删除优惠券","font-size:18px;color:#ba9963;font-weight:600"],btn:["确定","取消"],shadeClose:!0,btnAlign:"c",scrollbar:!1})});