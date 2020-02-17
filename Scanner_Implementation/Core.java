enum Core {
    // Keywords for program definition
    PROGRAM, //program
    BEGIN, //begin
    END,    //end

    // Keywords for declaring variables/functions
    INT, //int
    ENDFUNC, //endfunc

    // Keywords for while loop, if statements
    IF, //if
    THEN, //then
    ELSE, //else
    WHILE, //while
    ENDWHILE, //endwhile
    ENDIF, //endif

    // Special symbols
    SEMICOLON, //;
    LPAREN, // (
    RPAREN, // )
    COMMA, // ,
    ASSIGN, //:=
    NEGATION, //!
    OR, //or
    EQUAL, //=
    LESS, //<
    LESSEQUAL, //<=
    ADD, //+
    SUB, //-
    MULT, //*

    // Keywords for input/output statements
    INPUT, //input
    OUTPUT, //output

    // Tokens that pass with extra information
    CONST, //0-1023
    ID, // (a..z)|(A..Z)|(1..9)

    BLANK, // EMPTY SPACE

    //Special error
    ERROR,

    // Special token to indicate end of file
    EOS; //'EOF'


}
