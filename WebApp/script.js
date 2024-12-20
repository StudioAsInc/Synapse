// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-app.js";
import { getDatabase, ref, push, onValue } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-database.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-analytics.js";


// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyA1W0rNXV78mQV4WX3JljmPewRP1-DtZiU",
  authDomain: "synapse-social-sai.firebaseapp.com",
  databaseURL: "https://synapse-social-sai-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "synapse-social-sai",
  storageBucket: "synapse-social-sai.firebasestorage.app",
  messagingSenderId: "269633434355",
  appId: "1:269633434355:web:4b8a8c50f1001aeaa3bd86",
  measurementId: "G-3BLHJ42D92"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const database = getDatabase(app);


const textForm = document.getElementById('textForm');
const textInput = document.getElementById('textInput');
const textList = document.getElementById('textList');

// Function to handle form submission
textForm.addEventListener('submit', (e) => {
    e.preventDefault(); // Prevent default form submission
    const text = textInput.value.trim();
    if (text) {
        // Push text to Firebase Realtime Database
        const textsRef = ref(database, 'texts');
        push(textsRef, text)
          .then(() => {
             textInput.value = "";
         })
         .catch(error => {
             console.error("Error adding text:", error);
        });

    }
});

// Function to fetch data in realtime
const textsRef = ref(database, 'texts');
onValue(textsRef, (snapshot) => {
    textList.innerHTML = ''; // Clear the existing list
    const data = snapshot.val();
    if (data) {
        for (const key in data) {
            const textItem = document.createElement('li');
            textItem.textContent = data[key];
            textList.appendChild(textItem);
        }
    } else {
       // Display message when no data is available
       const noDataMessage = document.createElement('li');
       noDataMessage.textContent = 'No text yet';
       textList.appendChild(noDataMessage)

    }
});
