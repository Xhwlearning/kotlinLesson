import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*协程
    含义：是一个基于线程的上层框架；优势在于比线程更方便，能够更容易写出并发代码，能够在同一个代码块里进行多次的线程切换。
    代码块：在launch大括号“{线程123}”里的线程们组成的框架就是协程。
*/

//伪代码
launch(Dispatchers.Main){ // 主线程
    val user = api.getUser() //网络请求：后台线程
    nameTv.text = user.name //更新UI：主线程
    val image = withContext(Dispatchers.IO){ //withContext可以指定线程执行，并在执行完毕后自动切回来
        getImage(imageId)
    }
    avastarIv.setImageBitmap(image)
}

/*挂起函数
    1. 所谓挂起，就是切一个线程，例如主线程遇到耗时任务需要切一个后台线程；
    2. suspend：并不起挂起作用，而是起挂起提醒，提醒调用者我是耗时的，需要把我放在协程里面；
    3. 自定义的挂起函数里必须要包含一个自带的挂起函数才能起作用；
    4. 耗时（或需要等待）的任务一般都需要写成自定义的挂起函数，例如I/O操作和CPU计算工作；
*/

//伪代码
launch(Dispatchers.Main){ // 主线程
    ...
    val image = suspendingGetImage(imageId)
    avastarIv.setImageBitmap(image)
}
suspend fun suspendingGetImage(imageId : String){
    withContext(Dispatchers.IO){ //withContext就是一种自带的挂起函数
        getImage(imageId)
    }
}

