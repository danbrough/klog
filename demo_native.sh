
./gradlew :test:linkKlogDemoDebugExecutableLinuxX64 || exit 1

exec ./test/build/bin/linuxX64/klogDemoDebugExecutable/klogDemo.kexe $@

