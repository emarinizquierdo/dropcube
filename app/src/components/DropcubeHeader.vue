<template>
  <div class="dropcube-header-wrapper">
    <div class="toolbar">
      <a href="/devices" />
      <div
        class="main-title flex layout-row layout-align-start-center layout-align-xs-center-center layout-margin"
      >
        <a href="/">
          <span class="exo">dropcube</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss">
a {
  color: var(--dropcube-white);
}

.dropcube-header-wrapper {
  position: fixed;
  min-height: 64px;
  top: 0;
  left: 0;
  width: 100%;
  color: #fff;
  background-color: var(--dropcube-main-blue);
  z-index: 99999999999999999;
}

.logo-img {
  width: 35px;
  height: auto;
}

.main-title {
  margin-left: 30px;
  font-size: 31px;
  overflow: hidden;
  text-overflow: ellipsis;
}

.main-title i {
  font-weight: 100;
  font-style: normal;
}

@media (max-width: 639px) {
  .main-title {
    font-size: 25px;
  }
  .logo-img {
    width: 28px;
  }
}
</style>
