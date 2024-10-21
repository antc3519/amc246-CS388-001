# Android Project 4 - *Flixster-Shows*

Submitted by: **Anthony Caruso**

**Flixster-Shows** is a movie browsing app that allows users to browse popular TV shows through The Movie DataBase 

Time spent: **3** hours spent in total

## Required Features

The following **required** functionality is completed:

- [x] **Choose any endpoint on The MovieDB API except `now_playing`**
  - Chosen Endpoint: `[TODO: FILL ME IN TO GET CREDIT]`
- [x] **Make a request to your chosen endpoint and implement a RecyclerView to display all entries**
- [x] **Use Glide to load and display at least one image per entry**
- [x] **Click on an entry to view specific details about that entry using Intents**

The following **optional** features are implemented:

- [ ] **Add another API call and RecyclerView that lets the user interact with different data.** 
- [x] **Add rounded corners to the images using the Glide transformations**
- [ ] **Implement a shared element transition when user clicks into the details of a movie**

The following **additional** features are implemented:

- [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='(https://github.com/antc3519/amc246-CS388-001/blob/Flixster-Shows/Flixster-shows.gif)' title='Video Walkthrough' width='' alt='Video Walkthrough' />

<!-- Replace this with whatever GIF tool you used! -->
GIF created with LITEcap 
<!-- Recommended tools:
[Kap](https://getkap.co/) for macOS
[ScreenToGif](https://www.screentogif.com/) for Windows
[peek](https://github.com/phw/peek) for Linux. -->

## Notes

I had a few issues with access the API, it took me a while to figure out how to access the endpoints as TMDB does not provide documentation for the JSON structure, but my biggest problem 
was in the detail view. It was extremely difficult for me to layout the image, overview, etc. without scrolling off the page, so I added a scrollView which made spacing my views a nightmare. 
This is when I learned I can use scaleTypes on my image to make it not generate whitespace and look reasonably decent.


## License

    Copyright [2024] [Anthony Caruso]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
