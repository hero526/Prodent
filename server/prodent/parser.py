import requests
from bs4 import BeautifulSoup
import json
import os

DASE_DIR = os.path.dirname(os.path.abspath(__file__))

def parse_prodent():
    req = requests.get('https://0.0.0.0:8080/postdata/')
    json = req.text
    soup = BeautifulSoup(html, 'html.parser')
