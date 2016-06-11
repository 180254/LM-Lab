String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

var $grammar = $("#grammar");
var $sentence = $("#sentence");
var $regexp = $("#regexp");
var $result = $("#result");
var $check = $("#check");

var check = function () {
    $result.text("?");
    $regexp.val("");

    $.post("grammar.php", {
        grammar: $grammar.val().trim(),
        sentence: $sentence.val()

    }).done(function (data) {
        $result.text(data[0]);

        var regexp_nl = data[1].replaceAll("(?#END)", "(?#END)\r\n");
        $regexp.val(regexp_nl);
    }).fail(function () {
        $result.text("error?");
        $regexp.val("");
    })
};

$check.on("click", function () {
    check();
});

$sentence.keydown(function (event) {
    if (event.keyCode == 13) {
        check();
        return false;
    }
});

$grammar.val(`
<start>        ::= <expression2>{; <expression2>}[;]
<expression2>  ::= <expression1>{<operator><expression1>}
<expression1>  ::= (<expression2>)|<fraction>
<fraction>     ::= <integer>[.<digit0>{<digit0>}]
<integer>      ::= 0|<digit1>{<digit0>}
<digit0>       ::= 0|1|2|3|4|5|6|7|8|9
<digit1>       ::= 1|2|3|4|5|6|7|8|9
<operator>     ::= +|-|/|*|^
`.trim());

$sentence.val("(1.2*3)+5-(23.4+3)^(3*(0.02-(1.2*2+4)-1)); 8.2/4;");
