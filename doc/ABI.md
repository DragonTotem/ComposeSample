### What is ABI?
https://juejin.cn/post/6844904148589084680?searchId=202404151544029735E6786554A656C46B

ABI : Application Binary Interface(应用二进制接口)

不同Android设备，使用的CPU架构可能不同，因此支持不同的指令集。 CPU 与指令集的每种组合都有其自己的应用二进制界面（或 ABI）,ABI非常精确地定义了应用程序的机器代码应如何在运行时与系统交互。您必须为要与您的应用程序一起使用的每种CPU架构指定一个ABI（Application Binary Interface）。

### cd

zbt:/ $ uname -a
Linux zbt 4.19.193 #77 SMP PREEMPT Thu Mar 21 10:48:27 CST 2024 aarch64
zbt:/ $ getprop |grep ro.product.cpu.abi
[ro.product.cpu.abi]: [arm64-v8a]
[ro.product.cpu.abilist]: [arm64-v8a,armeabi-v7a,armeabi]
[ro.product.cpu.abilist32]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist64]: [arm64-v8a]

tb8788p1_64_bsp:/ $ uname -a
Linux localhost 4.4.146 #95 SMP PREEMPT Tue Feb 20 15:20:05 CST 2024 aarch64
tb8788p1_64_bsp:/ $ getprop |grep ro.product.cpu.abi
[ro.product.cpu.abi]: [arm64-v8a]
[ro.product.cpu.abilist]: [arm64-v8a,armeabi-v7a,armeabi]
[ro.product.cpu.abilist32]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist64]: [arm64-v8a]

peony-perf1:/ $ uname -a
Linux localhost 4.9.170 #1 SMP PREEMPT Mon Mar 4 20:06:10 CST 2024 armv8l
peony-perf1:/ $ getprop |grep ro.product.cpu.abi
[ro.product.cpu.abi]: [armeabi-v7a]
[ro.product.cpu.abi2]: [armeabi]
[ro.product.cpu.abilist]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist32]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist64]: []

lily-perf:/ $ uname -a
Linux localhost 4.9.56 #1 SMP PREEMPT Tue Sep 12 10:02:54 UTC 2023 armv7l
lily-perf:/ $ getprop |grep abi
[ro.product.cpu.abi]: [armeabi-v7a]
[ro.product.cpu.abi2]: [armeabi]
[ro.product.cpu.abilist]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist32]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist64]: []

meizu16Xs:/ $ uname -a
Linux localhost 4.14.83-perf+ #1 SMP PREEMPT Thu Dec 28 16:32:28 CST 2023 aarch64
meizu16Xs:/ $ getprop |grep ro.product.cpu.abi
[ro.product.cpu.abi]: [arm64-v8a]
[ro.product.cpu.abilist]: [arm64-v8a,armeabi-v7a,armeabi]
[ro.product.cpu.abilist32]: [armeabi-v7a,armeabi]
[ro.product.cpu.abilist64]: [arm64-v8a]

五个设备，有三种架构：
- armv7l
- armv8l
- aarch64

https://juejin.cn/post/7085665948794880031 aarch64指令架构集

"armv8l" 是 ARM 架构中的一种标识。它指示设备采用 ARMv8 64 位架构，其中 "l" 表示设备使用了 ARMv8 架构的 32 位兼容模式。

ARMv8 是一种 64 位指令集架构，它提供了更高的性能和更强大的功能。而 "armv8l" 表示设备支持 ARMv8 架构，并且在某些情况下可能以 32 位兼容模式运行。

需要注意的是，虽然设备支持 ARMv8 架构，但 "armv8l" 并不直接指示设备是 64 位还是 32 位。

armv7l" 是 ARM 架构中的一种标识。它指示设备采用 ARMv7 32 位架构。

ARMv7 是一种 32 位指令集架构，广泛用于许多移动设备和嵌入式系统。大多数较旧的 Android 设备都采用 ARMv7 架构。

"armv7l" 表示设备支持 ARMv7 架构，并且在 32 位模式下运行。

[[Android][NDK][Cmake]一文搞懂Android项目中的Cmake](https://juejin.cn/post/7320231441706991616?searchId=202404151544029735E6786554A656C46B)