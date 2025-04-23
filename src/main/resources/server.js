const express = require('express');
const axios = require('axios');
const app = express();
const port = 3000;
const cors = require('cors');

// Apply CORS (only requests from origin are allowed to access server resources)
app.use(cors({
    origin: 'http://localhost:8081',
}));

app.use(express.static('public'));

// Get data from Spring Boot API @/courses
app.get('/courses', async (req, res) => {
    try {
        const response = await axios.get('http://localhost:8080/courses/');
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching courses:', error);
        res.status(500).send('Error fetching courses');
    }
});

// Get data from Spring Boot API @/courses/all
app.get('/courses/all', async (req, res) => {
    try {
        const response = await axios.get('http://localhost:8080/courses/all');
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching all courses:', error);
        res.status(500).send('Error fetching courses');
    }
});

// Get data from Spring Boot API @/courses/:title
app.get('/courses/:title', async (req, res) => {
    const {
        title
    } = req.params;

    try {
        const response = await axios.get(`http://localhost:8080/courses/${title}`);
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching course by title:', error);
        res.status(500).send('Error fetching course by title');
    }
});

// Route to Spring Boot API @/courses/toggle-completion/:courseTitle
app.put('/courses/toggle-completion/:courseTitle', async (req, res) => {
    try {
        const courseTitle = decodeURIComponent(req.params.courseTitle);
        const response = await axios.put(`http://localhost:8080/courses/toggle-completion/${courseTitle}`);
        res.json(response.data);
    } catch (error) {
        console.error("Error toggling course completion:", error);
        res.status(500).json({ message: "Internal Server Error" });
    }
});

// Get data from Spring Boot API @/courses/categories
app.get('/courses/categories', async (req, res) => {
    try {
        const response = await axios.get('http://localhost:8080/courses/categories');
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching categories:', error);
        res.status(500).send('Error fetching categories');
    }
});

// Get remaining courses from Spring Boot API @/courses/remaining-courses
app.get('/courses/remaining-courses', async (req, res) => {
    try {
        const response = await axios.get('http://localhost:8080/courses/remaining-courses');
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching remaining courses:', error);
        res.status(500).send('Error fetching remaining courses');
    }
});

// Start server (port 3000)
app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});