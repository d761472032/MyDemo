var app = {
    method: {
        init: function () {
            app.$('title').innerText = 'This is home page.';
        }
    },
    $: function (id) {
        return document.getElementById(id);
    }
};