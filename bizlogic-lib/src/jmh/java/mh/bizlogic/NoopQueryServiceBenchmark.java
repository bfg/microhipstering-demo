package mh.bizlogic;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@Timeout(time = 10)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
@Fork(1)
public class NoopQueryServiceBenchmark {
    private QueryService service = new NoopQueryService();

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(1)
    public QueryResult dummyBenchmarkST() {
        return service.query("dummy", 42)
                .blockingGet();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Threads(Threads.MAX)
    public QueryResult dummyBenchmarkMT() {
        return service.query("dummy", 42)
                .blockingGet();
    }
}