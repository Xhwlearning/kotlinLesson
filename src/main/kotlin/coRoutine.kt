/*协程
    含义：是一个基于线程的上层框架；优势在于比线程更方便，能够用线性的结构写异步代码，能够在同一个代码块里进行多次的线程切换。
    代码块：在launch大括号“{线程123}”里的线程们组成的框架就是协程。
*/

//伪代码
GlobalScope.launch(Dispatchers.Main){ // 主线程
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

/*协程自带的用来提供后台线程池的ContinuationInterceptor，用于填充协程launch的输入参数
    Dispatcher调度器：
       后台线程：
        Default:线程数与CPU核数相同，用于计算密集型任务，例如图片压缩，媒体编解码；
        IO:线程数一般为64，因为IO数磁盘或者网卡在工作，CPU是比较空闲的，所以线程数可以分配的多，用于磁盘和网络访问工作；
       主线程：
        Main:例如UI线程。
*/