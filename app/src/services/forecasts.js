
var FORECASTS_URL = '/v1/forecasts';

export const getForecast = (id) => {

  let url = `${FORECASTS_URL}/${id}/list`;

  return new Promise((resolve, reject) => {

    fetch(url).then(res => resolve(res.json()))
      .catch(error => console.error('Error:', error))
      .catch(error => reject(error))

  });

}