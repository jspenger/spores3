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

VERSIONS = 3.3.6 \
	3.4.3 \
	3.5.2 \
	3.6.4 \
	3.7.1

.PHONY: cross-build

cross-build:
	/usr/bin/env bash -e -x -o pipefail -c; \
	for version in $(VERSIONS); do \
		sbt -Dsbt.server=false ++$${version}! -v compile; \
		sbt -Dsbt.server=false ++$${version}! -v test:compile; \
	done

.PHONY: cross-test

cross-test:
	/usr/bin/env bash -e -x -o pipefail -c; \
	for version in $(VERSIONS); do \
		sbt -Dsbt.server=false ++$${version}! -v test; \
	done

.PHONY: cross-test-sample
cross-test-sample:
	/usr/bin/env bash -e -x -o pipefail -c; \
	for version in $(VERSIONS); do \
		sbt -Dsbt.server=false ++$${version}! -v "sampleJVM / runMain spores.sample.Example"; \
		sbt -Dsbt.server=false ++$${version}! -v "sampleJVM / runMain spores.sample.LambdaExample"; \
		sbt -Dsbt.server=false ++$${version}! -v "sampleJS / run"; \
		sbt -Dsbt.server=false ++$${version}! -v "sampleNative / run"; \
	done

.PHONY: cross-sandbox

cross-sandbox:
	rm -rf .sandbox
	mkdir -p .sandbox
	rsync -a . .sandbox --exclude='.sandbox'
	cd .sandbox && $(MAKE) clean && $(MAKE) cross-build && $(MAKE) cross-test && $(MAKE) cross-test-sample

JVM_VERSIONS = 8.0.452-zulu 11.0.27-tem 17.0.15-tem 21.0.7-tem

.PHONY: paranoid

paranoid:
	/usr/bin/env bash -e -x -o pipefail -c; \
	for jvm in $(JVM_VERSIONS); do \
		echo "Testing with JAVA_HOME for JDK $${jvm}"; \
		. "$$HOME/.sdkman/bin/sdkman-init.sh"; \
		sdk use java $${jvm}; \
		/usr/bin/env bash -e -x -o pipefail -c; \
		java -version; \
		$(MAKE) clean; \
		$(MAKE) cross-sandbox; \
	done
