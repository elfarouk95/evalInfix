import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>?) {

    var res = evalInfix("10+2*(3+5)*4")

    println(res)

}


fun evalInfix(eq: String): String {

    var operList = listOf<Char>('+', '-', '*', '/')

    var numbers: Stack<Double> = Stack()

    var op: Stack<Char> = Stack()
    
    var i = 0
    while (i < eq.length) {

        var c = eq[i]

        if (c.isWhitespace())
            continue
        else if (c.isDigit() || c == '.') {
            var temp: String = ""

            do {
                temp += c
                i++
                if (i >= eq.length) break

                c = eq[i]
            } while (c.isDigit() || c == '.')

            numbers.push(temp.toDouble())

            i--
        } else if (c == '(')
            op.push(c)
        else if (c == ')') {
            while (op.peek() != '(')
                numbers.push(doOp(op.pop(), numbers.pop(), numbers.pop()))
            op.pop()
        } else if (c in operList) {

            while (!op.isEmpty() && !hasPrec(c, op.peek()))
                numbers.push(doOp(op.pop(), numbers.pop(), numbers.pop()))

            op.push(c)
        }


        i++
    }

    while (!op.isEmpty())
        numbers.push(doOp(op.pop(), numbers.pop(), numbers.pop()))

    return numbers.peek().toString();

}


fun hasPrec(op1: Char, op2: Char): Boolean {

    return !((op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/'))

}

fun doOp(op: Char, x: Double, y: Double): Double {

    when (op) {
        '+' -> return x + y
        '-' -> return x - y
        '*' -> return x * y
        '/' -> {
            if (y != 0.0) {
                return x / y;
            } else throw ArithmeticException("Divide by Zero")
        }
        else -> return 0.0
    }
}



