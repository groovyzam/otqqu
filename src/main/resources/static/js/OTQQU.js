$('#header_menu_post').mouseenter(function () {
    $('#header_menu_post').css('display', 'none');
    $('#header_menu_post_on').css('display', 'block');
});
$('#header_menu_post_on').mouseleave(function () {
    $('#header_menu_post_on').css('display', 'none');
    $('#header_menu_post').css('display', 'block');
});

$('#header_menu_account').mouseenter(function () {
    $('#header_menu_account').css('display', 'none');
    $('#header_menu_account_on').css('display', 'block');
});
$('#header_menu_account_on').mouseleave(function () {
    $('#header_menu_account_on').css('display', 'none');
    $('#header_menu_account').css('display', 'block');
});
$('.dropdown-content').mouseenter(function () {
    $('#header_menu_account').css('display', 'none');
    $('#header_menu_account_on').css('display', 'block');
});
$('.dropdown-content').mouseleave(function () {
    $('#header_menu_account_on').css('display', 'none');
    $('#header_menu_account').css('display', 'block');
});