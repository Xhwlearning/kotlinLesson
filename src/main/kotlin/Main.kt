//高阶函数
fun main()
{
    val input = readln()
    val output = input.myFilter {
        //it.isLetter()
        this.isLetter()
    }
    println(output)
}

fun String.myFilter(predicate : Char.() -> Boolean) : String{
    return buildString{
        for (ch in this@myFilter){
            if(ch.predicate()){
                append(ch)
            }
        }
    }
}
