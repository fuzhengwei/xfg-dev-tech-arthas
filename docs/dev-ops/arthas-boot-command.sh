# 需要单独手动执行

profiler start --event alloc

profiler getSamples

profiler status

profiler stop --format html

jad cn.bugstack.xfg.dev.tech.Application
