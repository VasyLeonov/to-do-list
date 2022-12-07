$(function(){

    const appendAffair = function(data){
        var affairCode = '<a href="#" class="affair-link" data-id="' +
            data.id + '">' + data.name + '</a> ';
        var deleteCode = '<a href="#" class="link-delete" data-id="' +
            data.id + '">' + "Удалить дело" + '</a>' + '<br>' + '<br>';
        $('#affair-list')
            .append('<div>' + affairCode + deleteCode + '</div>');
    };

    //Loading affair on load page
//    $.get('/affairs/', function(response)
//    {
//        for(i in response) {
//            appendAffair(response[i]);
//        }
//    });

    //Show adding affair form
    $('#show-add-affair-form').click(function(){
        $('#affair-form').css('display', 'flex');
    });

    //Closing adding affair form
    $('#affair-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting affair
    $(document).on('click', '.affair-link', function(){
        var link = $(this);
        var affairId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/affairs/' + affairId,
            success: function(response)
            {
                var code = '<span>Что нужно сделать: ' + response.title + '</span>' + '<br>'
                + '<span>Автор: ' + response.author + '<br>' + 'Дата: ' + response.date + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Дело не найдено!');
                }
            }
        });
        return false;
    });

    //Delete affair
    $(document).on('click', '.link-delete', function(){
            var link = $(this);
            var affairId = link.data('id');
            $.ajax({
                method: "DELETE",
                url: '/affairs/' + affairId,
                success: function(entry)
                {
                    link.parent().html(entry);
                    alert("Дело удалено");

                }
            });
            return false;
        });

    //Adding affair
    $('#save-affair').click(function()
    {
        var dataName = jQuery('input[name="name"]').val();
        var dataTitle = jQuery('input[name="title"]').val();
        var dataAuthor = jQuery('input[name="author"]').val();
        var dataDate = jQuery('input[data-date="date"]').val();
        $.ajax({
            method: "POST",
            contentType: "application/json",
            url: '/affairs/',
            data: JSON.stringify({name: dataName, title: dataTitle, author: dataAuthor, date: dataDate}),
            dataType: "json",
            success: function(response)
            {
                $('#affair-form').css('display', 'none');
                var affair = {};
                affair.id = response;
                var dataArray = $('#affair-form form').serializeArray();
                for(i in dataArray) {
                    affair[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendAffair(affair);
            }
        });
        return false;
    });
});