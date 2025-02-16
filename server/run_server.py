from flask import Flask, jsonify
import json

address = "192.168.0.107"
port = 21100


app = Flask(__name__, static_folder="images")

top_recipes = json.load(open('top_recipes.json', encoding='utf-8'))

@app.route('/top-films', methods=['GET'])
def get_top_recipes():
    return jsonify(top_recipes)


if __name__ == '__main__':
    app.run(host=address, port=port)
