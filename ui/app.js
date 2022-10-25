const express = require('express');
const bodyParser = require("body-parser");
const path = require("path");
const app = express();

app.use(express.static('public'))

app.use(bodyParser.urlencoded({ extended: false }));
app.set("view engine", "ejs");
app.use(express.static(path.join(__dirname, "public")));

app.get("/", (req, res) => {
  res.render("index", {
    pagetitle: "Tree Of Taste",
    ORDERENTRYAPIURL: process.env.ORDERENTRYAPIURL
  });
});

var server = app.listen(process.env.PORT, function () {
  console.log("Express server listening on port " + process.env.PORT);
});