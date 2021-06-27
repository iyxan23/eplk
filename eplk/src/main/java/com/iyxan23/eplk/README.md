# Welcome to the Source Code!
Here are some details of these class(es)/packages:
 - `Runner.kt` is a kotlin script where you can run eplk files
 - `Shell.kt` is a kotlin script where you can run the EPLK shell
 - `Utils.kt` just utilities outside the eplk language, like node pretty print, nothing much
 - `Tokens.kt` is simply a list of tokens
 - `errors/` is a package that contains custom errors used by the lexer/parser/interpreter to "throw" an error in EPLK
 - `lexer/` is a package that contains the Lexer, a part of an interpreted language that converts a code into a list of tokens, that will later be fed into the Parser
 - `parser/` is a package that contains the Parser, a part of an interpreted language that converts a list of tokens into an [AST](https://en.wikipedia.org/wiki/Abstract_syntax_tree) (Abstract Syntax Tree) that will later be fed into the Interpreter
 - `grammar.txt` is the grammar for the parser, it's mostly written in regex, but is implemented manually, this file is mostly just for reference
 - `nodes/` is a package that contains the nodes used in the AST (Abstract Syntax Tree)
 - `interpreter/` is a package that contains the Interpreter, it's just a simple visit function to a node, I made this singleton class so it would look nice in the code
