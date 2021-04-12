/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.openjdk;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@Fork(jvmArgsPrepend = {"-Djava.library.path=."})
@State(Scope.Benchmark)
public class MyBenchmark {
    static class Dummy {
        int field;
    }

    static native void jniCall();
    static native void nativeWX();
    static native void nativeNanoTime();

    static native void nativeInit(Class dummy);
    static native void nativeTestGetField(Dummy dummy, int loopCnt);

    static Dummy dummy = new Dummy();
    static final Runtime runtime = Runtime.getRuntime();

    static {
        System.loadLibrary("MyBenchmark");
        nativeInit(Dummy.class);
    }


    @Benchmark
    public void testNothing() {
        // no-op
    }

    @Benchmark
    public void testJNI() {
        // -> native_wrapper ; JNI -> native method ; no-op
        jniCall();
    }

    @Benchmark
    public void testWX() {
        // -> native_wrapper ; JNI -> native method -> W^X ; no-op
        nativeWX();
    }


    @Benchmark
    @Fork(jvmArgsPrepend = {
        "-Djava.library.path=.",
        "-XX:+DisableExplicitGC",
    })
    public void testTwoStateAndWX() {
        // -> native_wrapper ; JNI -> native method -> VM ; W^X ; no-op
        runtime.gc();
    }

    @Benchmark
    public void testJniNanoTime(Blackhole bh) {
        // -> native_wrapper ; JNI -> native method -> W^X, gettime
        nativeNanoTime();
    }

    @Benchmark
    public void testNanoTime(Blackhole bh) {
        // -> gettime
        bh.consume(System.nanoTime());
    }

    @Param({"1", "10", "100", "1000"})
    int loopCnt;

    @Benchmark
    public void testGetField() {
        nativeTestGetField(dummy, loopCnt);
    }
}
