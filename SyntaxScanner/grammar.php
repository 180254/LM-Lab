<?php
header("Content-Type: application/json");

function startsWith($haystack, $needle)
{
    return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== false;
}


function to_regexp($grammar)
{
    $DefinitionsPattern = '(<?[a-z0-9\']+>?)\s*::=\s*(.*)';
    $TerminalPattern = '([^<>{}\[\]|a-z0-9\'" ])';
    $RepetitionPat1 = '\{(.+?)\}';
    $RepetitionPat2 = '\[(.+?)\]';
    $RepetitionPat3 = '(?:\(\?:)?(.*?)\)?\(\?:\1\)\*';

    $symbols = [];
    $definitions = [];
    $length = count($grammar);

    // zamiana na parę symbol - definicja
    for ($i = 0; $i < $length; $i++) {
        $matches = null;
        if (!preg_match("@^${DefinitionsPattern}$@i", $grammar[$i], $matches)) {
            throw new Exception("Bad definition; index=${i}.");
        }

        $symbols[$i] = $matches[1];
        $definitions[$i] = $matches[2];
    }

    // zmiana symbolu z E na <E>
    for ($i = $length - 1; $i >= 0; $i--) {
        if (!startsWith($symbols[$i], "<")) {
            $sym = preg_quote($symbols[$i]);
            $symbols[$i] = "<{$symbols[$i]}>";

            for ($j = $length - 1; $j >= 0; $j--) {
                $definitions[$j] = preg_replace("@(^|[^<])(${sym})@", "$1<$2>", $definitions[$j]);
            }
        }
    }

    // ulepszamy definicje, tak by stala sie regexpem
    for ($i = 0; $i < $length; $i++) {
        $newDef = $definitions[$i];

        $newDef = preg_replace("@${TerminalPattern}@i", '\\\\$1', $newDef); // escape terminali
        $newDef = preg_replace("@${RepetitionPat1}@", '(?:$1)*', $newDef); // zamiana powtórzeń na regexp
        $newDef = preg_replace("@${RepetitionPat2}@", '(?:$1)?', $newDef); // zamiana powtórzeń na regexp
        $newDef = preg_replace("@${RepetitionPat3}@", '(?:$1)+', $newDef); // zamiana kombinacji aa* na a+

        $newDef = str_replace("0|1|2|3|4|5|6|7|8|9", '\d', $newDef); // uproszczenie zapisu wszystkie cyfry
        $newDef = str_replace("1|2|3|4|5|6|7|8|9", '[1-9]', $newDef); // uproszczenie zapisu cyfry bez zera

        $newDef = str_replace('\\\\0', '\d{0}', $newDef); // symbol pusty, czyli zero liczb

//        $newDefSplit = preg_split("@\\|@", $newDef);
//        if (count($newDefSplit) > 1) {
//            $newDef = '';
//            for ($j = 0, $c = count($newDefSplit); $j < $c; $j++) {
//                $newDef = $newDef . "(?:" . $newDefSplit[$j] . ")";
//                if ($j !== $c - 1) {
//                    $newDef = $newDef . "|";
//                }
//
//            }
//        }

        $comment = (string)($i + 1) . "-" . str_replace(["<", ">"], "", $symbols[$i]); // info jaki symbol w komentarzu
        $newDef = "(?#${comment})(?(DEFINE)(" . $newDef . "))(?#END)"; // zdefiniowanie wyrazenia dla definicji

        $definitions[$i] = $newDef;
    }

    // podstawiamy referencję do definicji we wszystkich odwołaniach
    for ($i = 0; $i < $length; $i++) {
        $sym = preg_quote($symbols[$i]);

        $iGroup = $i + 1;
        for ($j = 0; $j < $length; $j++) {
            $definitions[$j] = preg_replace("@${sym}@", "(?${iGroup})", $definitions[$j]);
        }
    }

    // sklejanie wszystkich definicji
    $result = "";
    for ($i = 0; $i < $length; $i++) {
        $result = "${result}{$definitions[$i]}";
    }
    // wywołanie definicji startowej
    $result = "${result}^(?1)$";

    return $result;
}

function handle_post()
{
    try {
        if (empty($_POST["grammar"])
            || !isset($_POST["sentence"])
        ) {
            throw  new Exception("Bad request, missing param.");
        }

        $regex = to_regexp(preg_split("/\\r?\\n/", $_POST["grammar"]));
        $match = preg_match("@${regex}@i", $_POST["sentence"]);

        echo json_encode([$match ? "ok" : "fail", $regex]);

    } catch (Exception $e) {
        echo json_encode(["parse_fail", $e->getMessage()]);
    }
}

handle_post();


