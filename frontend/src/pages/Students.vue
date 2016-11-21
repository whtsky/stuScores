<template>
  <div>
    I'm students!
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Students',
    computed: {
      ..mapGetters({
        'students',
        'classNames'
      }),
    },
  }
</script>