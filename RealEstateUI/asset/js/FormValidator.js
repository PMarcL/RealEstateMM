function isAFieldEmpty()
{
    var isAFieldEmpty = false;
    $('form').find(":input").each(function () {
        if ($(this).val() === "")
        {
            isAFieldEmpty = true;
        }
    });
    if($('select').val() === null)
    {
        isAFieldEmpty = true;
    }
    return isAFieldEmpty;
}