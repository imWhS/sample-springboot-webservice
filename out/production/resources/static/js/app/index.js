var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            alert('게시물 저장을 시도합니다');
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },

    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('Completed save.');
            window.location.href = '/';
        }).fail(function (error) {
            alert('fail save.')
        });
    },

    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('Completed update.');
            window.location.href = '/';
        }).fail(function (error) {
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            alert('Completed delete.');
            window.location.href = '/';
        }).fail(function (error) {
        });
    }

};

main.init();