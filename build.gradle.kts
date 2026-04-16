plugins {
	java
	id("org.springframework.boot") version "4.1.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "example.lombok"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-restclient")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("com.h2database:h2")

	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

	testCompileOnly("org.projectlombok:lombok")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
