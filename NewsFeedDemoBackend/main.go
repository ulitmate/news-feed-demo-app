package main

import (
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/mux"
	"net/http"
	"encoding/json"
	"fmt"
)

var db *sql.DB

type News struct {
	Id int64 `json:"id"`
	Category string `json:"category"`
	ImageUrl string `json:"image_url"`
	Title string `json:"title"`
	Description string `json:"description"`
	Source string `json:"source"`
}

func GetNewsEndpoint(response http.ResponseWriter, request *http.Request) {
	params := request.URL.Query()
	start := params["start"]
	fmt.Println(start)
	fmt.Println(start[0])
	// query for an incremental 5
	rows, err := db.Query("SELECT * FROM news LIMIT " + start[0] + ", 5")
	if err != nil {
		fmt.Println("error in rows")
		fmt.Println(err)
		http.Error(response, http.StatusText(500), 500)
		return
	}
	defer rows.Close()

	newsList := make([]News, 0)
	for rows.Next() {
		news := News{}
		err := rows.Scan(&news.Id, &news.Category, &news.ImageUrl, &news.Title, &news.Description, &news.Source)
		if err != nil {
			http.Error(response, http.StatusText(500), 500)
			return
		}
		newsList = append(newsList, news)
	}
	if err := rows.Err(); err != nil {
		http.Error(response, http.StatusText(500), 500)
		return
	}

	enc := json.NewEncoder(response)
	enc.SetIndent("", "\t")
	err = enc.Encode(newsList)
	if err != nil {
		panic(err)
	}
}

func TestEndpoint(response http.ResponseWriter, request *http.Request) {
	fmt.Fprintf(response, "Test")
}

func main() {
	var err error
	db, err = sql.Open("mysql", "root:pw1@tcp(127.0.0.1:8080)/newsfeedschema")
	if err != nil {
		panic(err)
	}
	defer db.Close()

	router := mux.NewRouter()
	router.HandleFunc("/test", TestEndpoint)
	router.HandleFunc("/news", GetNewsEndpoint).Methods("GET")
	http.ListenAndServe(":1234", router)
}
