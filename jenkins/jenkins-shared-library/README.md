# Jenkins Shared Library for Java Pipeline

This shared library converts the original declarative Jenkins pipeline into a reusable Jenkins Shared Library structure.

## Structure

- vars/: reusable functions that contain the shell commands and Jenkins step logic.
- src/com/company/pipeline/JavaPipeline.groovy: orchestration class that defines the declarative pipeline stages.
- Jenkinsfile: minimal entrypoint that loads the library and passes configuration.

## How it works

1. The Jenkinsfile loads the shared library and calls the JavaPipeline class.
2. The JavaPipeline class defines the declarative pipeline and coordinates stages.
3. Each stage invokes a reusable function from vars/.
4. The vars files contain the implementation details and commands.

## Example usage

In the Jenkinsfile, configure the values and call the pipeline with a map.

## Notes

- Replace the library name in the Jenkinsfile if your Jenkins configuration uses a different shared library name.
- Ensure the Jenkins agent label and credentials exist in your Jenkins environment.
