<template>

  <page-content page-title="About">
    <div class="main-content">
      <md-table-card>
        <md-toolbar>
          <h1 class="md-title">Nutrition</h1>
          <md-button class="md-icon-button">
            <md-icon>filter_list</md-icon>
          </md-button>

          <md-button class="md-icon-button">
            <md-icon>search</md-icon>
          </md-button>
        </md-toolbar>

        <md-table-alternate-header md-selected-label="selected">
          <md-button class="md-icon-button">
            <md-icon>delete</md-icon>
          </md-button>

          <md-button class="md-icon-button">
            <md-icon>more_vert</md-icon>
          </md-button>
        </md-table-alternate-header>

        <md-table md-sort="calories">
          <md-table-header>
            <md-table-row>
              <md-table-head md-sort-by="dessert">Dessert (100g serving)</md-table-head>
              <md-table-head md-sort-by="type" width="100px">Type</md-table-head>
              <md-table-head md-sort-by="calories" md-numeric md-tooltip="The total amount of food energy and the given serving size">Calories (g)</md-table-head>
              <md-table-head md-sort-by="fat" md-numeric>Fat (g)</md-table-head>
              <md-table-head>
                <md-icon>message</md-icon>
                <span>Comments</span>
              </md-table-head>
            </md-table-row>
          </md-table-header>

          <md-table-body>
            <md-table-row v-for="(row, rowIndex) in nutrition" :key="rowIndex" :md-item="row" md-selection>
              <md-table-cell v-for="(column, columnIndex) in row" :key="columnIndex" :md-numeric="columnIndex !== 'dessert' && columnIndex !== 'comment' && columnIndex !== 'type'">
                <span v-if="columnIndex === 'comment'">{{ column }}</span>

                <md-button class="md-icon-button" v-if="columnIndex === 'comment'">
                  <md-icon>edit</md-icon>
                </md-button>

                <md-select
                  placeholder="Type"
                  :name="'type' + columnIndex"
                  :id="'type' + columnIndex"
                  v-model="nutrition[rowIndex].type"
                  v-if="columnIndex === 'type'">
                  <md-option value="ice_cream">Ice Cream</md-option>
                  <md-option value="pastry">Pastry</md-option>
                  <md-option value="other">Other</md-option>
                </md-select>

                <span v-if="columnIndex !== 'type' && columnIndex !== 'comment'">{{ column }}</span>
              </md-table-cell>
            </md-table-row>
          </md-table-body>
        </md-table>
      </md-table-card>
    </div>
  </page-content>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Scores',
    computed: {
      ...mapGetters([
        'scores',
        'studentNames',
        'examNames'
      ]),
    },
    data: () => ({
      nutrition: [
        {
          dessert: 'Frozen yogurt',
          type: 'ice_cream',
          calories: '159',
          fat: '6.0',
          comment: 'Icy'
        },
        {
          dessert: 'Ice cream sandwich',
          type: 'ice_cream',
          calories: '237',
          fat: '9.0',
          comment: 'Super Tasty'
        },
        {
          dessert: 'Eclair',
          type: 'pastry',
          calories: '262',
          fat: '16.0',
          comment: ''
        },
        {
          dessert: 'Cupcake',
          type: 'pastry',
          calories: '305',
          fat: '3.7',
          comment: ''
        },
        {
          dessert: 'Gingerbread',
          type: 'other',
          calories: '356',
          fat: '16.0',
          comment: ''
        }
      ],
    })
  }
</script>
