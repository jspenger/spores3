.PHONY: build

build:
	sbt -Dsbt.server=false -v compile
	sbt -Dsbt.server=false -v test:compile

.PHONY: clean

clean:
	find . -name .DS_Store | xargs rm -fr
	find . -name .metals | xargs rm -fr
	find . -name .vscode | xargs rm -fr
	find . -name .idea | xargs rm -fr
	find . -name .bsp | xargs rm -fr
	find . -name .bloop | xargs rm -fr
	find . -name "*.json" | xargs rm -fr
	find . -name metals.sbt | xargs rm -fr
	rm -fr project/project/
	find . -name target | xargs rm -fr

.PHONY: test

test:
	sbt -Dsbt.server=false -v test

.PHONY: test-sample

test-sample:
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.BuilderExample"
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.LambdaExample"
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.AutoCaptureExample"
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.AgentMain"
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.Futures"
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.FutureMap"
	sbt -Dsbt.server=false -v "sampleJVM / runMain spores.sample.ParallelTreeReduction"
	sbt -Dsbt.server=false -v "sampleJS / run"
	sbt -Dsbt.server=false -v "sampleNative / run"

.PHONY: sandbox

sandbox:
	rm -rf .sandbox
	mkdir -p .sandbox
	rsync -a . .sandbox --exclude='.sandbox'
	cd .sandbox && $(MAKE) clean && $(MAKE) build && $(MAKE) test && $(MAKE) test-sample
