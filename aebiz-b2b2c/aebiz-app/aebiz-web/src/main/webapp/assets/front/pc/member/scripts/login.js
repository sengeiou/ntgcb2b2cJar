"use strict";$(function(){$(".icon-cuo1").click(function(){$(this).css("display","none"),$(this).parents("li").find("input.string").val("").focus()})}),$("input.string").bind("input propertychange",function(){$(this).parents("li").find(".icon-cuo1").css("display","inline-block")});