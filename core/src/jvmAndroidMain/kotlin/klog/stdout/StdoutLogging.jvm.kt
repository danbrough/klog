package klog.stdout

expect fun printMethodStderr(): PrintMethod = {
  System.err.println(it?.toString())
}
