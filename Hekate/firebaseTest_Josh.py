from firebase import firebase
firebase = firebase.FirebaseApplication('https://hekate.firebaseio.com', None)

# results = firebase.get("hekate", None)

firebase.patch("User1", {"ping": "168.168.1.1"})

# firebase.delete("Commands", 'cmd')
# firebase.delete("Commands", 'Ping')


def shownewentry(results):
    print(results)

# firebase.on("child_added", shownewentry(snapshot))