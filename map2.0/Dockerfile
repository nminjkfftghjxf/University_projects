# Use an official lightweight C image as a base
FROM gcc:latest

# Set the working directory
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY ex26.c /app/


# Compile the C code
RUN gcc -o ex25 ex26.c

# Specify the command to run on container start
CMD ["./app"]


