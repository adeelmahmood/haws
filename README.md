#Hadoop on AWS (HAWS)

This branch contains the code for uploading local files to AWS S3 storage.

Follow these steps to test the code.

### Register for AWS Free Tier

[AWS Free Tier](https://aws.amazon.com/free/)

### Obtain credentials from AWS

1. Login to AWS Management Console
2. Click on your name on top right
3. Click on Security Credentials
4. (Optional) If the popup shows up, Continue to Security Credentials
5. Open Access Keys section
6. Create New Access Key
7. Download Key File (and save on your disk). This file has two properties that we will need later. AccessKey and SecretKey

### Clone the repository

```bash
git clone -b file-s3-connector https://github.com/adeelmahmood/haws.git
cd haws
```

### Provide AWS Credentials to HAWS application

```bash
> cd connectors/src/main/resources/
> vim aws-credentials.properties
Hit "i" to go into insert mode
Paste the access key and secret key from the key file downloaded from AWS
Make sure to name the keys as "accessKey" and "secretKey" (without the quotes)
Hit ":wq" to save the contents and exit
```

Your aws-credentials.properties file should look like this

```bash
> cat aws-credentials.properties 
accessKey=BKs212IAJGADajshU32Easdjh8876KA
secretKey=1233jSsasd23o6qh6vasdjklasdjt9BKgRPjkk423jkhkfdhjkf
```

### Compile the code

```bash
> cd ../../../../ (to go back to root folder)
> ./gradlew build
...
...
Build Successful
```

Now we have a compiled jar to run the application with. Before running the application lets understand what we are trying to do. We want to upload some files from a directory in our local computer to S3. So first we need to create some random files to upload

### Generate random files to upload

```bash
> mkdir ~/s3 (create a new folder in your home directory)
> cd ~/s3
> for i in {1..10}; do dd if=/dev/urandom bs=1000000 count=10 of=file$i; done 
above command will generate 10 files of 9.5MB each, you can modify the 1..10 to generate more files or modify the bs and count params to increase or decrease the size of generated files
```

### Run the application

Finally we can run the application to upload these generated files to our S3 storage. You need to provide a few properties to the application to know where to upload from and where to upload to.

```
> cd - (to go back to haws directory, if this doesnt works cd to that directory manually)
> java -Dinput.dir=/Users/adeelq/s3 -Ds3.bucket=yourname-bucket-001 -Ds3.remote.dir=files -jar connectors/build/libs/haws-connectors-0.0.1.SNAPSHOT.jar

You will need to adjust the params specified in this command
-Dinput.dir = where your local files are (full path)
-Ds3.bucket = name of your s3 bucket (application will create it for you, so just specify anything)
-Ds3.remote.dir = name of folder where files should be copied in S3
```

The application will start with HAWS logo and then you should see a bunch of log output. After that you should see messages indicating that upload is in progress. Wait until you see Upload completed for all 10 files. Then you can go to your AWS console and in S3 you should your new bucket with all the files uploaded !!!

```
