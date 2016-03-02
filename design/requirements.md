# Mixxy

Developed by www.purple, consisting of:

- Jesse Talavera-Greenberg
- Brian Sabzjadid
- Kathleen Cleary
- Cristiano Miranda

## Table of Contents

This will be written out at the end -- man, I wish we could generate this automatically...can we?

## Introduction

The purpose of this document is to specify our plans for the development of Mixxy, a Web-based collaborative art platform.  Specifically, we discuss the relevance of such a service, the functionality we see as required in order to declare Mixxy a fulfilling experience, and the challenges we expect to encounter in the development process.

We opted for a holistic approach in the creation of this document; we give preference to simplicity and conciseness over strict conformance to a standard, expecting that a reader will be able to parse and understand this document in its entirety.  We also expect that the reader is able and willing to contact us regarding clarifications, and that this document will be updated accordingly.

### Scope

This document is written to specify the following of Mixxy:

- High-level requirements and constraints
- The mission-critical data, on a high level
- Use cases
- The problem solved

The following are *not* within the scope of this document:

- Detailed information about data storage requirements
- Specification of algorithms or functionality in a formal syntax
- A detailed analysis of the target audience
- Business plan and monetization details
- Architecture, software design, or other implementation issues
- Technology choices for non-critical components (e.g. utility libraries, parsers)

### Definitions, acronyms, and abbreviations

**User (n.):** A person who registers for an account with Mixxy and partakes in its services.  Does not necessarily contribute content (but we'd like them to).

**Moderator (n.):** A user who enforces community standards and responds to violations, taking disciplinary actions if necessary.

**Work (n.):** Any content a user has posted to Mixxy, typically an illustration.

**Remix (v.):** The act of one user making a copy of another's work for the purpose of editing, improving upon, or otherwise modifying the original work.

**Remix (n.):** The result of a user's modifications to a work.

*When remixing a work, the original is not modified in the process.  Remixing cannot be used for vandalism.*

**18+ (adj.):** Prominent depiction of any of the following themes:

  - Explicit sexual themes (e.g. nudity or pornography)
  - Realistic violence and gore
  - Excessive strong language
  - Extreme treatment of controversial topics

*18+ content is fully permitted, so long as it is categorized as such by its creator.*

**Forbidden (adj.):** Any presence of the following themes:

  - Child pornography, bestiality, rape, and other illegal sexual activity
  - Copyright violations
  - Hateful speech, harassment, and cyber-bullying
  - Threats of violence or other disruptive activity
  - Encouragement of criminal activity
  - External links to any of the above

*Under no circumstances may forbidden content be posted.  Forbidden content and any remixes thereof will be forcibly removed, and if necessary the relevant authorities will be contacted.*

### References

That IEEE document, I guess?  I dunno, maybe we'll need to fill this out later.

## Overall description

### Rationale

With the advent of social media, much of digital culture now revolves around freely sharing--and occasionally modifying--user-made content.  Mixxy is designed with this trend in mind, to empower artists and illustrators of all skills to put their own twist on an existing work.  How this is applied is left entirely to the user base -- community-built stories, collaborative works, and even outright parody come to mind, though by no means should they be seen as limits.  Clever and emergent uses of the platform are highly encouraged.

More formally, Mixxy is an art platform where users can not only post and share their own work, but freely remix that of others.  Users may provide or remix content with either the provided in-browser image editor or through their own preferred toolset, uploading their work to Mixxy upon its completion.  Users can discuss works with one another, follow creators they admire, or find works based on a particular subject matter.

Mixxy is *not* designed as:

- A platform to exchange works for monetary value
- A static art gallery or portfolio Web page
- A conventional image sharing/hosting service or imageboard (e.g. Imgur, 4chan)
- A recruiting service for artists
- A repository of artwork for use in other media (e.g. clip art websites, Open Game Art)
- A tutorial service for art and illustration skills

Users are, however, welcome to use Mixxy for these purposes unless otherwise stated.

### User Interfaces

The table below summarizes the ways with which the user will interact with Mixxy, which will be further detailed using UML Use Case diagrams.  Note that "Clicks" always refers to a left-mouse-button click, as so does "Drags". These Use-Case  diagrams should be fed as input directly into Section 3.1, external interfaces, which is where the design  of the user interface is specified. Here is the full list of UML Use-Case Diagrams:

#### Sign In
|                      Use case | Sign in |
|------------------------------:|---------|
|                 Primary actor |User, Admin|
|               Goal in context | The user may at any point log in to the website with their username and password. Note that the user can only log in to one session.|
|                 Preconditions | The application has no user session. |
|                       Trigger | The user clicks on the “Sign In” button|
|                      Steps    | <ol><li>User starts the web application, which loads the homepage</li><li> User clicks on the “Sign In” button</li></ol>|
|                    Exceptions | This button should always be enabled when the user isn't logged in. Note that should a user already be logged in, the application shouldn't display this button.|
|                      Priority | Essential, must be implemented |
|                When available |         |
|              Frequency of use | Used as often as a user will log in and out of their account. |  

#### Sign Out
|                      Use case | Sign out |
|------------------------------:|---------|
|                 Primary actor |User, Admin|
|               Goal in context | The user may at any point log out from the website. Note that the user can only log out from one session.|
|                 Preconditions | The application has a current user session. |
|                       Trigger | The user clicks on the “Sign Out” button|
|                      Steps    | <ol><li>User starts the web application, which loads the homepage</li><li> User clicks on the “Sign Out” button</li></ol>|
|                    Exceptions | This button should always be enabled when the user is logged in. Note that should a user already be logged out, the application shouldn't display this button.|
|                      Priority | Essential, must be implemented |
|                When available |         |
|              Frequency of use | Used as often as a user will log in and out of their account. |

#### Draw Comic
|                      Use case | Draw Comic  |
|------------------------------:|-------------|
|                 Primary actor | User
|               Goal in context | Create a new comic
|                 Preconditions | User is logged in
|                       Trigger | Click 'Create New' button from Draw tab
|                      Scenario | <ol><li>Actor clicks 'create new' button from Draw menu</li><li>Muro loads in browser, allowing user to create their comic</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>       
|                    Exceptions | user is not logged in             
|              Frequency of use | Frequently - we anticipate a good amount of content creation   

#### Upload Comic
|                      Use case | Upload Comic  |
|------------------------------:|---------------|
|                 Primary actor | User
|               Goal in context | Upload a previously created comic
|                 Preconditions | User is logged in
|                       Trigger | Click 'Upload' button from Draw tab
|                      Scenario | <ol><li>Actor clicks upload button from Draw menu</li><li>File chooser loads, prompting actor to select file to be uploaded</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>       
|                    Exceptions | Wrong file format, user is not logged in                    
|              Frequency of use | Frequently - we anticipate a good amount of image uploading    

#### Edit Comic
|                      Use case | Edit Comic  |
|------------------------------:|-------------|
|                 Primary actor | User
|               Goal in context | Edit a preexisting comic
|                 Preconditions | User is logged in
|                       Trigger | Click 'edit' link in sidebar of owned comic
|                      Scenario | <ol><li>Actor opens comic, clicks 'edit' in the sidebar</li><li>Comic is opened in Muro for editing</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>    
|                    Exceptions | user does not own comic            
|              Frequency of use | Infrequently

#### Remix Comic
|                      Use case | Remix Comic  |
|------------------------------:|-------------|
|                 Primary actor | User
|               Goal in context | Remix a preexisting comic
|                 Preconditions | User is logged in, viewing a comic
|                       Trigger | Click 'remix' link in sidebar of comic
|                      Scenario | <ol><li>Actor opens comic, clicks 'remix' in the sidebar</li><li>Comic is opened in Muro for remixing</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>    
|                    Exceptions | user is not logged in            
|              Frequency of use | Infrequently

#### Delete Comic
|                      Use case | Delete Comic |
|------------------------------:|--------------|
|                 Primary actor | User, Admin
|               Goal in context | Delete a comic owned by the user, or violating TOS
|                 Preconditions | User is logged in as comic owner, or is an authenticated admin
|                       Trigger | Click 'delete' link in sidebar of owned comic
|                      Scenario | <ol><li>Actor opens comic, clicks delete in the sidebar</li><li>Popup message appears asking actor to confirm deletion</li><li><ul><li>If actor confirms, comic is deleted</li><li>If actor clicks cancel, they are returned to comic</li></ul></li></ol>       
|                    Exceptions | User does not own comic
|              Frequency of use | Infrequently  

#### Basic Search
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User, Lurker
|               Goal in context | Find a work given broad criteria
|                 Preconditions | None
|                       Trigger | User enters a single text string as search criteria
|                         Steps | <ol><li>Enter text string in search field</li><li>Navigate through results</li><li>Navigate to any desired works that fit the criterion</li></ol>
|                    Exceptions | No existing works may fit the requested criteria.  The user will be notified of this.
|              Frequency of use | At will

#### Advanced Search
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User, Lurker
|               Goal in context | Find a work given multiple specific criteria
|                 Preconditions | None
|                       Trigger | User navigates to the advanced search page
|                         Steps | <ol><li>Provide criteria based on authorship, popularity, age, and tags, among other thing</li><li>Navigate through results</li><li>Navigate to any desired works that fit the criterion</li></ol>
|                    Exceptions | No existing works may fit the requested criteria.  The user will be notified of this.
|              Frequency of use | At will

#### Register
|                      Use case | Register|
|------------------------------:|---------|
|                 Primary actor |User, Admin|
|               Goal in context | The user may at any point register for a new account from the website. Note that the user can only register for an account if they aren't logged in already.|
|                 Preconditions | The application has no user session. |
|                       Trigger | The user clicks on the “Register” button|
|                      Steps    | <ol><li>User starts the web application, which loads the homepage</li><li> User clicks on the “Register” button</li></ol>|
|                    Exceptions | This button should always be enabled when the user is not logged in. Note that should a user already be logged in, the application shouldn't display this button.|
|                      Priority | Essential, must be implemented |
|                When available |         |
|              Frequency of use | Used as often as a user will decide to create a new account. |

#### Tagging Works
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Categorize a work based on its content for easy retrieval
|                 Preconditions | Work in question must belong to user
|                       Trigger | User creates, remixes, or edits a work
|                         Steps | <ol><li>Enter delimited text strings that categorize the work</li><li>Finalize submission or update of work</li></ol>
|                    Exceptions | Certain tags may be reserved, or may only be used conditionally.  In such cases, the user may not post or update their work.
|              Frequency of use | When creating or updating a work

#### Navigating to Similar Works
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User, Lurker
|               Goal in context | Find works with similar content that the user might enjoy.
|                 Preconditions | Work in question must belong to user
|                       Trigger | User navigates to related works near existing ones
|                         Steps | <ol><li>Given a small palette of similar works, navigate to any that are appealing.</li></ol>
|                    Exceptions | Not enough similar works may exist.  In practice, the definition of "similar" will be broadened, but in the early days there may simply not be enough works to go around.
|              Frequency of use | While viewing any work

#### Ban User
|                      Use case |Ban a User |
|------------------------------:|---------|
|                 Primary actor |Admin|
|               Goal in context |The admin may at any point ban/kick a user from the website for violating the website's guidelines. Note that the user has to exist for this feature to be applicable.|
|                 Preconditions | The user being banned/kicked exists in the Database. |
|                       Trigger | The admin clicks on a "ban/kick" button for a specific user. |
|                      Steps    | <ol><li>Admin starts the web application, which loads the homepage</li><li> Admin locates the user and clicks on the “ban/kick” button</li></ol>|
|                    Exceptions | This button should always be enabled when the user exists and is not banned/kicked already. Note that should a user already be banned/kicked, the application shouldn't display this button. |
|                      Priority | Essential, must be implemented |
|                When available |         |
|              Frequency of use | Used as often as an admin feels that a user is violating the guidelines. |

#### Reset Password
|                      Use case | Forgot a password |
|------------------------------:|---------|
|                 Primary actor |User, Admin|
|               Goal in context |The user may at any point request a change password form incase they have forgotten their password. Note that the user has to exist and have a password for this feature to be applicable.|
|                 Preconditions | The user has no logged in session. |
|                       Trigger | The user clicks on the "Forgot Password" button. |
|                      Steps    | <ol><li>User starts the web application, which loads the homepage</li><li> User locates the "Sign In" button which tkes them to the Sign In page.</li><li>The user enters their username or email and clicks on the "Forgot Password" button.</li></ol>|
|                    Exceptions | This button should always be enabled when the user exists and is not logged in yet. |
|                      Priority | Essential, must be implemented |
|                When available |         |
|              Frequency of use | Used as often as the user feels that they forgot their password. |

#### Post Comment
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Write a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Click on comment text field.</li><li>Write comment.</li><li>Click on comment button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|              Frequency of use | At will.

#### Up Comment
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Up a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Navigate to comment.</li><li>Click Up button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|              Frequency of use | At will.

#### Down Comment
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Down a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Navigate to comment.</li><li>Click Down button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|              Frequency of use | At will.

#### Flag Comment
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Flag a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Navigate to comment.</li><li>Click Flag button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|              Frequency of use | At will.

#### Like Comic
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Like a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to a comic.
|                         Steps | <ol><li>Navigate to comic.</li><li>Click on Like button.</li></ol>
|                    Exceptions | User is not logged in.
|              Frequency of use | At will.

#### Flag Comic
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Flag a comic with forbidden content.
|                 Preconditions | User must be logged in, viewing a commic.
|                       Trigger | User navigates to a comic.
|                         Steps | <ol><li>Navigate to comic.</li><li>Click on Flag button.</li></ol>
|                    Exceptions | User is not logged in.
|              Frequency of use | At will.

#### Share Comic
| Use Case                      | Information 
|-------------------------------|-----------|
|                 Primary actor | User
|               Goal in context | Share comic on social networks.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to a comic.
|                         Steps | <ol><li>Navigate to comic.</li><li>Click on Share button.</li><li>Pick social network to share to.</li></ol>
|                    Exceptions | User is not logged in.
|              Frequency of use | At will.

### Target audience

Mixxy is designed primarily for the benefit of artists and their enthusiasts, with particular attention to the comic fanbase.  We expect that most potential users are young (typically ages 15-30) and reasonably tech-savvy.

Children under the age of 13 may not use Mixxy, because we do not have the resources to comply with the Children's Online Privacy Protection Act.

### Constraints

The following issues arose in the design of Mixxy, all of which affect our options as developers:

- Content will need some form of moderation, in order to prevent Mixxy from being associated with illegal or unsavory material (e.g. child pornography).
- We cannot allow children under the age of 13 to use Mixxy, as we are not prepared to comply with COPPA.
- For legal reasons, all user works posted on Mixxy must be released under a license that permits free distribution and modification.
- We have no budget to speak of.
- As we are constrained to Google App Engine and Java 7, we may not be able to use certain libraries and tools.
- The in-browser editor must support raster image editing.  This implies that user works must be stored in raster format, resulting in higher storage and bandwidth requirements.

### Assumptions and Dependencies

In designing Mixxy, we have assumed the following:

- Users will have a desktop or laptop computer
- Users will have a recent Web browser installed
  - Said browser must support modern Web standards such as HTML5, `<canvas>`, and the like.
- Users are comfortable with allowing other users to modify their work.
- Not all followers of a given user are themselves skilled artists.
- Users do not necessarily have accounts with other social platforms (e.g. DeviantArt)
- Users are vigilant enough to report any violation of Mixxy's code of conduct.

This document may require revision if any of the stated assumptions are violated.

## Specific requirements

### External interfaces

The user will be able to remix the comics of their choosing on the web app via access from their laptop or desktop computers. They will be able to post, comment, like that comic, and if they so choose, they can subscribe to the user who post tasteful comics to receive a feed of their posts. The iOS app will be a compliment to the web app, where users get to view the comics they have remixed and other user content.

### Functions

Now we discuss things we actually have to do.  UI mockups will probably be dotted around here

#### Thing
  - List of things
  - Let's keep it concise

#### Other thing
  - This is text
  - I like words

### Performance requirements

Should we expect the user to go make a cup of coffee while waiting for something to happen?  (Hint: no)

### Logical database requirements

**Data Technology:** The data technology we will be using for this project will primarily be the GAE standard low-level Datastore Java API since it utilizes the Datastore to its full potential.

App Engine Datastore is a schemaless NoSQL datastore providing robust, scalable storage for your web application, with the following features:
- No planned downtime
- Atomic transactions
- High availability of reads and writes
- Strong consistency for reads and ancestor queries
- Eventual consistency for all other queries

**Stored Database Entities:** 

- **Users**: The user will be given attributes of all its account information such as: username, first name, last name, phone number, along with references to their comic creations and favorited comics. The User will also have a role attribute for escalated privilege that will allow them to carry their duties, such as: banning a user, suspending a user, deleting a remixed or original comic, if they are a moderator.

- **Comics**: Rendered raster images for the comic, original user that it was created by, and user that remixed the comic will be stored as a Comics Entity. We will be using DeviantArt Muro and most of these comics created by the users will be stored on DeviantArt. The comics created by non-DeviantArt users will be stored as a raster image in the database and will need a considerable amount of storage. 


### Design constraints

What restrictions (not conscious choices; I.e. don't say "we use Bootstrap") must we consider when designing this app?

### Software system attributes

What ultimately describes what Mixxy should be?  Yes, the usualy gamut of "reliable", "secure", etc. but remember, this is for people.  "Fun", "social", etc. are just as important

### Additional comments

I write more things
