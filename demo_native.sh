
./gradlew :demo:linkKlogDemoDebugExecutableLinuxX64 || exit 1

exec ./demo/build/bin/linuxX64/klogDemoDebugExecutable/klogDemo.kexe $@

