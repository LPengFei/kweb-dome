function  m_init(){
    across_scroll.prototype={
	        config:{
	            capacity:null,        //适应屏幕 展示的数量
	            disX:0
	        },
	        init:function(elem,box){

	            var elem_parent=elem.parent();
	            var screenWidth=$(document.body).outerWidth(true);
	            var elem_parentWidth=elem.outerWidth(true)*elem.length+120;
	            var arrow_show={left:false,right:false};                 //左右坐标默认隐藏   
	            var _this=this;

	            this.capacityChange(elem,box,elem_parent);      //当前页面可视几张图片

	            $(window).resize(function(){ 
	               
	               _this.capacityChange(elem,box,elem_parent);
	               elem.width((box.width()-120)/_this.config.capacity);
	               elem_parentWidth=elem.outerWidth(true)*elem.length+120;
	            })
	            
	            $("#bjui-sidebar .lock").click(function(){
	            	_this.capacityChange(elem,box,elem_parent);
	               elem.width((box.width()-120)/_this.config.capacity);
	               elem_parentWidth=elem.outerWidth(true)*elem.length+120;
	            })
	            
	            this.config.disX=elem_parent.offset().left-elem_parent.parent().offset().left;    //ul距离父级左侧的距离
	            

	            if(elem.length>3){
	                arrow_show.right=true;
	            }

	            $(window).resize(function(){
	               _this.config.disX=0;
	               elem_parent.css('left','0'); 
	               arrow_show.right=true;
	               arrow_show.left=false;
	               _this.arrowChange(arrows,arrow_show,elem); 
	            })

	            var arrows=this.createArrow(box);                //添加左右按钮,并取得元素
	            this.arrowChange(arrows,arrow_show,elem);                //按钮状态
	            this._move(arrows,arrow_show,elem_parent,elem);         //运动
	            
	            
	        },

	        createArrow:function(box){
	            var arrow='\
	                <span class="fa fa-angle-right credential_arrow arrow_right"></span>\
	                <span class="fa fa-angle-left credential_arrow arrow_left"></span>\
	                '
	            box.append(arrow);
	            return {right_arrow:box.find('.arrow_right'),left_arrow:box.find('.arrow_left')}
	        },

	        _move:function(arrows,arrow_show,elem_parent,elem){                
	            var _this=this;
	            for(var i in arrows){
	              $(arrows[i]).on('click',function(){
	                    
	                  if($(this).hasClass('arrow_left')&&arrow_show.left){
	                      _this._moveLeft(arrow_show,elem_parent,elem,arrows);
	                      _this.config.disX+=elem.outerWidth(true);
	                  }
	                  if($(this).hasClass('arrow_right')&&arrow_show.right){
	                      _this._moveRight(arrow_show,elem_parent,elem,arrows);
	                      _this.config.disX-=elem.outerWidth(true);
	                  }
	              })
	            }
	            
	        },

	        _moveLeft:function(arrow_show,elem_parent,elem,arrows){

	            if(this.config.disX>=-elem.outerWidth(true)){
	               arrow_show.left=false;
	               arrow_show.right=true;
	               this.arrowChange(arrows,arrow_show,elem);
	               
	            }else{
	               arrow_show.left=arrow_show.right=true;
	               this.arrowChange(arrows,arrow_show,elem);
	               
	            }
	             elem_parent.stop(false,true).animate({'left':`+=${elem.outerWidth(true)}px`},300);
	          
	        },

	        _moveRight:function(arrow_show,elem_parent,elem,arrows){
	            if(this.config.disX<=-(elem.length-this.config.capacity-1)*elem.outerWidth(true)){
	               arrow_show.left=true;
	               arrow_show.right=false;
	               this.arrowChange(arrows,arrow_show,elem);
	              
	           }else{
	               arrow_show.left=arrow_show.right=true;
	               this.arrowChange(arrows,arrow_show,elem);
	            }
	          elem_parent.stop(false,true).animate({'left':`-=${elem.outerWidth(true)}px`},300);
	        },

	        arrowChange:function(arrows,arrow_show,elem){             
	               
	                if(arrow_show.right){   
	                    arrows.right_arrow.show()
	                }else{
	                    arrows.right_arrow.hide()
	                }

	                if(arrow_show.left){
	                    arrows.left_arrow.show()
	                }else{
	                    arrows.left_arrow.hide()
	                }
	            },

	        capacityChange: function(elem,box,elem_parent){     
	            screenWidth=$(document.body).outerWidth(true);
	            if(screenWidth>1493){
	               this.config.capacity=3; 
	            }
	            else if(screenWidth<=1493&&screenWidth>1133){
	               this.config.capacity=2
	            }else if(screenWidth<=1133){
	               this.config.capacity=1
	            }
	            elem.width((box.width()-120)/this.config.capacity);
	            elem_parentWidth=elem.outerWidth(true)*elem.length+120;
	            elem_parent.css('width',`${elem_parentWidth}px`);        //初始化ul的宽度 
	        }
	    }

	    function across_scroll(){
	        this.init.apply(this,arguments)
	    }

	    new across_scroll($('.credential_list ul li'),$('.credential_list_box'));
	    $.fancyboxInit('.fancy_arr');
}