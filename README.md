Endpoints:

all:
post localhost:7070/register
{
    "username": "username",
    "password": "password",
    "role": "ROLE_MODERATOR",
    "email": "email@email.com"
}

user:
post localhost:7070/api/upload
get localhost:7070/api/images?sortBy=uploadDate&size=266&uploadDate=2024-06-16
get localhost:7070/api/download/1c85e6c8-9fbf-432c-a5c7-683dc402e3b0-4454955_2386457.jpg

moderator:
get localhost:7070/api/moderator/images?sortBy=uploadDate&size=266&uploadDate=2024-06-16
patch localhost:7070/api/moderator/block/{username}
patch localhost:7070/api/moderator/unblock/{username}