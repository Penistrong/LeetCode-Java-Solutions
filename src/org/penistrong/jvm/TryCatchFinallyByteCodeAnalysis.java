package org.penistrong.jvm;

public class TryCatchFinallyByteCodeAnalysis {

    // JVM编译try..catch...finally语句块时，为了保证finally块中语句一定会被执行
    // 在每个try{}、catch{}块的字节码指令后面都追加了一份finally语句块的字节码
    public int tryCatchFinallyExecuteSequence () {
        // try{}中有return语句时，执行完try中语句，将return要返回的变量值保存在局部变量表的槽里(区别于原变量i存放的槽，是个临时槽)
        // 从字节码可以看到执行完i=10后，在return前执行`iload_1`从局部变量表的槽1中取出变量i的值压入操作数栈顶
        // 然后执行`istore_2`将操作数栈顶的变量i的值存入槽2里，接着执行复制的finally语句块，保证finally块被执行
        // 最后再执行`iload_2`将try{}中要返回的值压入操作数栈顶，执行`ireturn`返回
        // 注意:
        // 如果finally块中也有return语句的话，最后不会加载前面暂存在槽2里的返回值，而是直接`iload_1`返回在finally{}中被修改的i的值
        int i = 1;
        try {
            i = 10;
            return i;
        } finally {
            i = 50;
            // 取消下面注释再对比字节码的差异
            // return i;
        }
    }

    public static void main(String[] args) {
        System.out.println(new TryCatchFinallyByteCodeAnalysis()
                .tryCatchFinallyExecuteSequence());
    }
}
