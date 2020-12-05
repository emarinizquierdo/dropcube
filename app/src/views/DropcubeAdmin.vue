<template>
  <div>
    <div class="layout horizontal wrap">
      <div v-for="(hour, index) in hours" :key="index">
        <input type="checkbox" id="checkbox" v-model="hour.value" />
        <label for="checkbox">{{index}}</label>
        <div class="color-box" :style="{'background-color': `#${forecasts[index]}`}"></div>
      </div>
    </div>
    <button @click="onSave">guardar</button>
  </div>
</template>

<script>
import { getDevice, setDevice } from "@/services/devices";
import { getForecast } from "@/services/forecasts";

export default {
  data() {
    return {
      hours: [
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false },
        { value: false }
      ],
      forecasts: []
    };
  },
  created() {
    getDevice(2715387)
      .then(device => {
        this.hours = device.hours.map(item => (item = { value: item }));
      })
      .catch(() => {});

    getForecast(2715387)
      .then(forecast => {
        this.forecasts = forecast.forecasts;
      })
      .catch(() => {});
  },
  methods: {
    onSave() {
      setDevice(
        2715387,
        this.hours.map(item => item.value)
      )
        .then(device => {
          this.hours = device.hours.map(item => (item = { value: item }));
        })
        .catch(() => {});
    }
  }
};
</script>

<style lang="scss" scoped>
.color-box {
  width: 20px;
  height: 20px;
}
</style>