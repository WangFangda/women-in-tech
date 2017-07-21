import json
import requests
from json import dumps
from json import loads
from bs4 import BeautifulSoup
from pymongo import MongoClient

class Location(object):
    def __init__(self, lat, lng):
        self.type = "Point"
        self.coordinates = []
        self.coordinates.append(lng)
        self.coordinates.append(lat)

class Place(object):
    def __init__(self, name, lat, lng):
        self.name = name
        self.location = Location(lat, lng)
    # def __repr__(self):
    #     return str(vars(self))

class RawHotel(object):
    def __init__(self, name, lat, lng):
        self.name = name
        self.lat = lat
        self.lng = lng
    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.__dict__ == other.__dict__
        return False
    def __ne__(self, other):
        return (not self.__eq__(other))
    def __hash__(self):
        return hash(tuple(sorted(self.__dict__.items())))

class Hotel(object):
    def __init__(self, name, lat, lng):
        self.name = name
        self.location = Location(lat, lng)
    # def __repr__(self):
    #     return str(vars(self))

class Route():
    def __init__(self, places):
        self.places = places
    # def __repr__(self):
    #     return str(vars(self))

class Itinerary():
    def __init__(self, userId, routes, hotels):
        self.userId = userId
        self.count = len(routes)
        self.routes = routes
        self.hotels = hotels
    # def __repr__(self):
    #     return str(vars(self))

def getMongoDb():
    client = MongoClient()
    db = client['women-in-tech']
    return db

def getUrls():
    f = open('C:/Users/fangda.wang/women-in-tech/clawer/output.txt', 'r')
    urls = list()
    for line in f:
        urls.append(line)
    return urls

def getItinerary(db, id, url):
    print('\n\n\n[', id, ']')

    print(url)

    response = requests.get(url)
    data = response.text
    soup = BeautifulSoup(data, "lxml")

    days = soup.findAll("div", {"class": "day"})

    rawHotels = set()

    routes = list()
    for day in days:
        places = list()
        for article in day.findAll('article'):
            lat = article.get('data-lat')
            lng = article.get('data-lng')
            name = article.get('data-enname')
            type = article.get('data-type')
            if (not lat):
                continue
            if (not lng):
                continue
            if (not name):
                continue

            lat = float(lat)
            lng = float(lng)
            if ("airport" in name.lower()):
                continue
            if (type == 'hotel'):
                rawHotels.add(RawHotel(name, lat, lng))
            elif (type == 'poi'):
                places.append(Place(name, lat, lng))
        if (len(places) > 0):
            routes.append(Route(places))

    if (len(rawHotels) > 0):
        hotels = list()
        for rawHotel in rawHotels:
            hotels.append(Hotel(rawHotel.name, rawHotel.lat, rawHotel.lng))
        itinerary = Itinerary(id, routes, hotels)
        itineraryValue = dumps(itinerary, default=lambda x: x.__dict__).encode('utf8')
        itineraryValue = loads(itineraryValue)
        db.itineraries.insert_one(itineraryValue)
        print(itineraryValue)
    else:
        print('no hotel information')




def main(urls):
    db = getMongoDb()
    for id, url in enumerate(urls, start=1):
        if id < 5:
            getItinerary(db, id, url)

if __name__ == '__main__':
    urls = getUrls()
    main(urls)