$(document).ready(function(){
            $('.btn-delete').click(function(){
                if (confirm ('Действительно удалить?')){
                    return true;
                }
                return false;
            })
        });