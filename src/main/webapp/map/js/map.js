/**
 * Created by lakewoodmy on 2017/6/28.
 */
$(function(){
    //左侧列表分类
    $('.cnksi_count_classify span').on('click',function(){
        var count_index=$('.cnksi_count_box .cnksi_count_box_detail').eq($(this).index());
        
        $(this).addClass('current').siblings().removeClass('current');
        count_index.siblings().stop(true,false).fadeOut('fast',function(){
            count_index.fadeIn('fast')
        })
    });
    //右侧列表分类
    

    $('.cnksi_right_list span').on('click',function(){
        $('.rigth_list ._scroll').remove();
        var list_index=$('.cnksi_index_right .rigth_list .list_detail').eq($(this).index());
        $(this).addClass('current').siblings().removeClass('current');
        list_index.addClass('active').siblings().removeClass('active');
        $('#right_list_box').xb_scroll(false);
    });
    //右侧列表显示隐藏
    var rightBar_right=$('.cnksi_index_right').outerWidth(); 
    $('.cnksi_index_right').css('right',`-${rightBar_right}px`)
    $(window).resize(function(){
        rightBar_right=$('.cnksi_index_right').outerWidth();
        
        if(!$('.right_open').find('.open')[0]){
            $('.cnksi_index_right').css('right',`-${rightBar_right}px`)
        }
    })
    
    $('.right_open').on('click',function(){
       $(this).find('img').toggleClass('open');
        if($(this).find('.open')[0]){
            $('.cnksi_index_right').animate({'right':'30px'});
            $('.cnksi_li_explain').animate({'right':'380'})
        }else{
            $('.cnksi_index_right').animate({'right':`-${rightBar_right}px`});
            $('.cnksi_li_explain').animate({'right':'100px'});
        }

    });
    //右侧列表展示子栏目
    $('.cnksi_index_right .rigth_list .list_detail ul>li .detail_bg').on('click',function(ev){
        $(this).siblings('ol').stop(true,false).slideToggle(function(){
            $('.rigth_list ._scroll').remove();
            $('#right_list_box').xb_scroll(false);

        });
    });

    $('#right_list_box').xb_scroll(false);
    
    var diog_drag=false;
    $('.cnksi_explain_category').bind('mousedown',function(e){
        diog_drag=true;
        disX=e.pageX-$('.cnksi_explain_category').offset().left;
        disY=e.pageY-$('.cnksi_explain_category').offset().top;
        $(document).bind('mousemove',function(e){
            if(diog_drag){
               $('.cnksi_explain_category').css('left',`${e.pageX-disX}px`);
               $('.cnksi_explain_category').css('top',`${e.pageY-disY}px`);
            }
            
        })
        $(document).mouseup(function(e){
            diog_drag=false;
            return false;
        })
    })

    $('#container').css('border','none');


    
});