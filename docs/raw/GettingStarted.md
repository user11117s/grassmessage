# Getting Started

## Installation
### Gradle

Add this to your `build.gradle` or `build.gradle.kts`:
```kotlin
repositories {
    // ...
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // ...
    compileOnly("com.github.user11117s:grassmessage:$VER:api")
    runtimeOnly("com.github.user11117s:grassmessage:$VER")
}
```

### Maven

Add this to your `pom.xml`:
```xml
<repositories>
    <!-- ... -->
    
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- ... -->
    
    <dependency>
        <groupId>com.github.user11117s</groupId>
        <artifactId>grassmessage</artifactId>
        <version>$VER</version>
    </dependency>
</dependencies>
```

Now you have declared a dependency on grassmessage!

### Autocomplete

This step is required for XML autocomplete to work. Download the required files to your `src/main/resources` folder with this command:
```shell
curl https://cdn.jsdelivr.net/gh/user11117s/grassmessage@v$VER/src/main/resources/xml.tar.gz -o t.tgz; tar -xzf t.tgz; rm t.tgz;
```

After running the command:
- **If your editor is a JetBrains IDE**, you should have autocomplete ready.
- **If your editor is not a JetBrains IDE**, you will need to add `src/main/resources/grassmessage/catalog.xml` to your editor's list of XML Catalog files. You should search up how to do this with your editor.

## Writing your first message

Create a file called `messages.xml` in your `resources` folder. Then, paste this code in it:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<grass xmlns="urn:grassmessage" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:grassmessage urn:grassmessage:schema:v1">
    <messages>
        <message name="hello">
            Hello, World!
        </message>
    </messages>
</grass>
```

Then, in your initialization code, write the following:

```java
// URL to your messages file
URL messages = getClass().getResource("/messages.xml");
Grass grass = Grass.create(messages);

try {
    grass.parse();
}
catch(IOException e) {
    // handle I/O exception here
}

Component hello = grass.createMessageInstance("hello").build();
```

If you try to display `hello` using the Adventure library, you should now see `Hello, World!` displayed on your screen!