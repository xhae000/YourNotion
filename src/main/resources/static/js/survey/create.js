$(document).ready(function(){
        let option_cnt = Array.from({length : 10}, () => 1);
        let question_cnt = 1;

        $('.age-input').click(function(){
            $(this).select();
        });

        $('.age-input').change(function(){
            if($(this).val()=='') return;
            const age = parseInt($(this).val());

            // 유저가 1 미만의 수를 입력하면 1로 보정
            if(age<1){
                $(this).val('1');
            }
            // 유저가 99 초과의 수를 입력하면 99로 보정
            else if(age>99){
                $(this).val('99');
            }
            // 유저가 만일 '01'을 입력했을 때 '1'로 보정
            else{
                $(this).val(age);
            }
        });

        $('body').on('click','.add-option' , function(){
            const qId = parseInt($(this).data('question-id'));

            option_cnt[qId] += 1;

            if(option_cnt[qId] == 5){
                $('.option-select').css('display','none');
            }


            $('[data-option-cont-id='+qId +']').append(
                "<div class='obj-option' data-question-id='"+qId+"'>"+
                "<input type='radio' onclick='return false' /> "+
                "<input class='option-input'  placeholder='옵션을 입력해주세요.' /> "+
                "<div class='delete-option'>×</div>"+
                "</div>"
            );

        });

    $('body').on('click','.add-etc' , function(){
        const qId = parseInt($(this).parent().find('.add-option').data('question-id'));

        option_cnt[qId] += 1;

        if(option_cnt[qId] == 5){
            $('.option-select').css('display','none');
        }

        $('[data-option-cont-id='+qId +']').append(
                `
                <div class='obj-option' data-question-id="`+qId+`">
                    <input type='radio' onclick='return false' />
                    <span class="etc-input">기타 (주관식)</span>
                    <input class="option-input" type="hidden" value="etc" />
                    <div class='delete-option'>×</div>
                </div>
`

        );

    });



    $('body').on('click','.delete-option',function(){
            const qId = parseInt($(this).parent().data('question-id'));
            const oId = parseInt($(this).parent().data('option-id'));

            if(option_cnt[qId] == 1){
                alert("옵션은 적어도 하나 이상 있어야합니다.");
                return;
            }
            else{
                option_cnt[qId] -=1;
                $(this).parent().remove();
                $('.option-select').css('display','');
            }
        });


        $('body').on('click','.type-obj' , function(){
            $(this).parent().parent().parent().find('.option').find('.obj-option')
                .css('display','')
        });

        $('body').on('click','.type-sub' , function(){
            $(this).parent().parent().parent().find('.option').find('.obj-option')
                .css('display','none')
        });

        $('body').on('click', '.delete-question', function(){
            if(question_cnt == 1){
                alert("질문은 적어도 하나 이상 있어야합니다.");
                return;
            }
            question_cnt -=1;

            $(this).parent().parent().remove();
        });

        $('.plus-icon').click(function(){
            if(question_cnt == 25){
                alert("질문은 25개까지만 생성할 수 있습니다.");
                return;
            }

          $('.question-list').append(`
              <div class="question-box">
                  <div class="question">
                      <input class="question-input" name="question[`+question_cnt+`].question" placeholder="질문을 입력해주세요." maxLength="100"/>
                  </div>
                  <div class="question-type">
                      <label><input class="type-obj" name="question[`+question_cnt+`].question_type" value="obj" type="radio"
                                    checked/> 객관식</label>
                      <label><input class="type-sub" name="question[`+question_cnt+`].question_type" value="sub"
                                    type="radio"/> 주관식</label>
                      <label></label>
                  </div>
                  
                  <div class="image-cont" data-image-id="`+question_cnt+`">
                                     <img src="" class="image-preview" /><br>
                                     <div class="delete-image">
                                         삭제
                                     </div>                  
                  </div>
        
        
                  <div class="option">
                      <div class="obj-option-cont">
                          <div data-option-cont-id="`+question_cnt+`" class="new-option-cont">
        
                              <div class="obj-option" data-question-id="`+question_cnt+`">
                                  <input type="radio" onClick="return false"/>
                                  <input class="option-input" maxlength="50" placeholder="옵션을 입력해주세요."/>
                                  <div class="delete-option">×</div>
                              </div>
        
                          </div>
                          <div class="obj-option">
                              <div class="option-select">
                                  <input type="radio" onClick="return false"/>
                                  <span data-question-id="`+question_cnt+`" class="add-option">옵션 추가</span> 또는 <span class="add-etc">'기타' 추가</span>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="question-menu">
                                    <div class="add-image">
                                        <label class="img-label"><input data-file-id="`+question_cnt+`" name="question[`+question_cnt+`].image" type="file" accept="image/*"/>사진 올리기</label>
                                    </div>
                                    <div class="delete-question">
                                        질문 삭제
                                    </div>
                   </div>
              </div>`
          );
            question_cnt += 1;

        });


        $('body').on('change','input[type="file"]', function (){

            if(this.files){
                const qId = $(this).data('file-id');
                if(!setImage(qId, this)) return false; //

                $('[data-image-id='+qId+']').css('display','block');
            }
            else{ // 업로드 버튼은 누르고, 취소만 눌렀을 때 : input value를 비우지 않는다 (삭제 버튼으로 비울거임)
                return false;
            }
        });


        $('body').on('click','.delete-image', function(){
            const qId = $(this).parent().data('image-id');

            $(this).parent().css('display','none');
            $("[data-file-id="+qId+"]").val('');
        });



        function setImage(qId, e){
            let reader = new FileReader();
            const image_input = $('[data-image-id='+qId+']').children('img');

            const ext = $(e).val().split('.').pop().toLowerCase();

            if($.inArray(ext, ["jpg","jpeg","png"]) == -1){
                alert("jpg, jpeg, png 확장자만 업로드 가능합니다.");
                return false;
            }

            reader.onload = function(e){
                image_input.attr('src',e.target.result);
            }

            reader.readAsDataURL(e.files[0]);

            return true;
        }


        $('.submit-btn').click(function(){
            const title = $('.title-input').val();
            const des = $('.des-textarea').val();

            if(isEmpty(title)){
                alert("설문의 제목을 입력해주세요.");
                $('.title-input').focus();
                return
            }
            if(isEmpty(des)){
                alert("설문의 내용을 설명해주세요.");
                $('.des-textarea').focus();
                return
            }
            if(parseInt($('.start-age').val())>parseInt($('.end-age').val())){
                alert("설문 대상의 연령 입력이 잘못되었습니다.");
                $('.start-age').focus();
                return;
            }

            let isError = false;


            $('.question-input').each(function(i,q){
                if(isEmpty($(q).val())){
                    alert("질문을 입력해주세요.");
                    $(q).focus();
                    isError = true;

                    return;
                }
            });

            if(isError){
                return;
            }

            let option_li = new Array();

            $('.option-input').each(function(i,o){
                var qId = $(o).parent().data('question-id');

                if(isEmpty($(o).val()) &&
                    $(o).parent().css('display')!="none"
                    ){
                    // 주관식이면 옵션 입력 없음

                    alert("옵션을 입력해주세요.");
                    $(o).focus();
                    isError = true;

                    return;
                }

                // [1, 질문, 1, 질문, 2, 질문]
                option_li.push(qId);
                option_li.push($(o).val());
            });

            if(isError){
                return;
            }
            else{
                $('.option_hidden').val(option_li);
            }

            $('form').submit();
        });



        // input 검사 함수 (빈칸이 아닌지)
        function isEmpty(str){
            const none_space_str = str.replace(' ','');

            if(none_space_str == ''){
                return true;
            }

            return false;
        }
});