# Instructions

## Installation

### IDE

[Visual Studio Code](https://code.visualstudio.com/) is recommended for navigating the repo, though you can use any IDE you are familiar with. 

### Repo
- Clone the repository on your local machine: [like this](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
- Install node LTS from [here](https://nodejs.org)
- Open the cloned repository in a terminal e.g. `cd leeds-trinity-assessment-task/client`
- Run `npm i`

## Bug Fixing

The index.js file is written to simulate very badly written code you might find in legacy software. First try running the code to see the errors that are produced. Once you've done this, look at index.js to make the following changes:

Fix the following code issues to get the repo working:

`foodApi.getOutlets().then(data => console.log('Get outlets call completed'));`

Change this to:

`foodApi.getOutlets(null).then(data => console.log('Get outlets call completed'));`

Next change one of these:

```
var config = {
        method: 'PUT'
    };
```

This should be:

```
var config = {
        method: 'GET'
    };
```

Then there are a couple more places where the code needs changing:

`foodApi.getFoodsByOutlet(1.2).then(data => console.log('Get foods by outlet call completed'));`

This code needs changing to read:

`foodApi.getFoodsByOutlet(1).then(data => console.log('Get foods by outlet call completed'));`

This line should be commented out or removed: 

```
const url = `foods/v1/${outletId}/${queryString}`;
```

This line should be uncommented to take its place:

```
//const url = `foods/v1/${outletId}${queryString ? '/' : ''}${queryString}`;
```

Please note that these are not necessarily always the best ways to fix the problems and in some cases could be papering over other issues.
If you look in the code comments you'll find some hints about what else might be wrong or better ways to fix things. As such, feel free
to deviate from the above instructions if you are sure you know a better way.

## Setting Environment Variables

You'll need to set an environment variable to point at the remote API. In a real environment you might well have to ask around to find out the value for this, although in an ideal world it would be either documented or made available to you as soon as you start on a project. In practice, particularly in consultancy or contracting work, you'll often have to ask multiple people, many of whom might be busy, stressed out or force you through a complex process to get this kind of information. Here, we will give you the URL you need.

### MacOS and Ubuntu Linux

Type `export BASE_TRINITY_PATH=https://leeds-trinity-api--vs3x78e.livelyplant-f1fa8836.ukwest.azurecontainerapps.io/task-api` into the terminal.

To check this has worked, type `echo $BASE_TRINITY_PATH`. The output should be 

> https://leeds-trinity-api--vs3x78e.livelyplant-f1fa8836.ukwest.azurecontainerapps.io/task-api

This will set the environment variable temporarily, which is all you need right now. Make sure to do this in the same terminal you run the solution from or it will not work.

### Windows

For Windows we're going to do things a slightly different way by creating a `.env` file. This method differs from the Mac/Linux methodology because we are setting these *only* for Node and not for the OS itself/the terminal.

If you look at the `.gitignore` file in the root directory, you'll notice we have set this file so it's not checked in to source control. This is a good practice.

To set your Node environment variable, first create a `.env` file, either in your IDE or by typing `notepad .env` from the command line. Add the following line `BASE_TRINITY_PATH=https://leeds-trinity-api--vs3x78e.livelyplant-f1fa8836.ukwest.azurecontainerapps.io/task-api` then save the file.

## Running the Solution

Type `npm run start` into the terminal for Linux or Mac, or `npm run win-start` for Windows. If everything is working, you should receive the following text within the output:

- Get outlets call completed
- Get foods call completed
- Get foods by outlet call completed

If for any reason it's not possible to get to this stage, or you do not see this in the output, please ask for assistance.

## Marks

Base: One mark is awarded for successfully setting up the environment variable. One mark for adding the missing forward slash to `BASE_TRINITY_PATH`. A further one mark is then awarded for each API call that is completed -- demonstrated by the bulleted text above being successfully output to the console. 5/5 scores 100% for the assessment. For students who finish before the allotted time, extra credit can be earned by addressing the issues noted in the code comments, and answering the questions in the 'extra credit' section below.

## Extra Credit

The output still contains a lot of errors and noise. Can you get rid of all of that, so there's just the `call completed` messages?

Would it be possible to run the solution without setting an environment variable at all, perhaps by doing something in the `package.json`? Could you figure out why we might prefer to do it this way?

The repo also includes the server code which can be run locally. Can you take the server code and get it working on your local machine? Can you get the client service successfully running against this local server instance?

### Mac/Linux

There is a way to set the environment variable permanently, so it will work from other terminals and after you restart the machine. Can you get this working?

### Windows

We don't actually have to set the `.env` file and can directly manipulate the values in the Windows GUI to pull through with just `node index.js`, skipping the `package.json` script entirely. Going even further, we could create a powershell script to automatically setup the local environment for new developers. Any idea how you could go about doing that? 
