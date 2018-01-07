// JavaScript Document

//content 切换
$(function(){
	$(".vi_content > div.vi_con:gt(0)").hide();
	$(".vi_title li:first").addClass("current");
	$(".vi_title li").click(function(){
		var index1=$(this).index();
		$(".vi_content > div.vi_con").eq(index1).show().siblings().hide();
		$(this).addClass("current").siblings().removeClass("current");
	});	
});

//产品、设备页滑动到相应位置浮动定位
$(window).scroll( function (){   
var  h_num=$(window).scrollTop();   
     if (h_num>100){   
        $( '.tag' ).addClass( 'fixer' );    
    } else {   
        $( '.tag' ).removeClass( 'fixer' );            
    }              
});  

//index lunbo
$(document).ready(function(e) {
});

//3d
$(function(){
	var timer = new Array;
	$('.im_c1t').hover(function(){
		var index = $('.sbul .li').index(this);
		var o = $(this).find('i');
		var i=Math.min(0,o.data('i'));
		clearInterval(timer[index]);
		timer[index] = setInterval(function(){
			i-=140;
			if(i<=-6860){
				clearInterval(timer[index]);
			}
			o.css('backgroundPosition','0 '+i+'px');
			o.data('i',i);
		},10)
	},function(){
		var index = $('.im_c1t').index(this);
		var o = $(this).find('i');
		var i=o.data('i');
		clearInterval(timer[index]);
		timer[index] = setInterval(function(){
			i+=140;
			if(i>=0){
				clearInterval(timer[index]);
			}
			o.css('backgroundPosition','0 '+i+'px');
			o.data('i',i);
		},10)
	})
})





