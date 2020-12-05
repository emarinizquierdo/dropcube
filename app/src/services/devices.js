
var DEVICES_URL = '/v1/devices';

export const getDevice = (id) => {

  let url = `${DEVICES_URL}/${id}`;

  return new Promise((resolve, reject) => {

    fetch(url).then(res => resolve(res.json()))
      .catch(error => console.error('Error:', error))
      .catch(error => reject(error))

  });

}

export const setDevice = (id, hours) => {

  let url = `${DEVICES_URL}/${id}`;

  return new Promise((resolve, reject) => {

    fetch(url, {
      method: 'PUT', // or 'PUT'
      body: JSON.stringify({hours}), // data can be `string` or {object}!
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(res => resolve(res.json()))
      .catch(error => console.error('Error:', error))
      .catch(error => reject(error))

  });

}