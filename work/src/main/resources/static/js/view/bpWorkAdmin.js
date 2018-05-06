const BP_WORK_ADMIN_VIEW = {
    template: `
<div class="content" style="width:925px;">
    <div>
        <h2 class="inline-block left">{{ month }} 勤怠入力</h2>
        <div class="inline-block right text-right">
            <h2 >合計勤務時間</h2>
            <h2>{{ sumWorkTime }} 時間</h2>
        </div>
    </div>
    <div class="clear"></div>
    <div class="center" style="margin-top:10px;">
        <table>
            <colgroup>
                <col style="width:100px;"/>
                <col style="width:100px;"/>
                <col style="width:100px;"/>
                <col style="width:100px;"/>
                <col style="width:100px;"/>
                <col style="width:200px;"/>
                <col style="width:150px;"/>
            </colgroup>
            <tr>
                <th style="height:40px;">日付</th>
                <th>開始</th>
                <th>終了</th>
                <th>休憩時間</th>
                <th>勤務時間</th>
                <th>備考</th>
                <th></th>
            </tr>
            <tr　v-for="(day, index) in dayList" style="height:20px;">
                <td class="text-center" style="height:50px;">{{ day.day | dateFormat }}</td>
                <td><input type="time" class="time" v-model="day.start" step="1800"/></td>
                <td><input type="time" class="time" v-model="day.end" step="1800"/></td>
                <td><input type="number" class="time" v-model="day.breakTime" step="0.5"/></td>
                <td><input type="number" class="time" v-model="day.workTime" step="0.5" readonly="true"/></td>
                <td><input type="text" v-model="day.note" step="0.5"/></td>
                <td class="text-center">
                    <b-button v-on:click="addStandard(index)">標準</b-button>
                    <b-button>反映</b-button>
                </td>
            </tr>
        </table>
    </div>
</div>
` ,
    data: function () {
        const dayList = [];
        var month = "";

        return {
            name: "sample-user-name",
            month: "",
            dayList: dayList,
            sumWorkTime: 0
        };
    }
    ,
    created: function() {
        const self = this;
        axios.post('/bpWorkAdmin/findList').then(result => {
            const data = result.data;
            self.month = data.month + "月";
            data.list.forEach(function(value) {
                const day = {
                    day: new Date(value.dayTime),
                    start: "",
                    end: "",
                    breakTime: "",
                    workTime: "",
                    note: ""
                };
                self.dayList.push(day);
            })
        });
    }
    ,
    methods: {
        addStandard: function(key) {
            const self = this;
            self.dayList[key].breakTime = "1";
            self.dayList[key].start = "09:00";
            self.dayList[key].end = "17:30";
        }
    }
    ,
    filters: {
        dateFormat: function (date) {
            return formatDate(date, "MM月DD日");
        }
    }
    ,
    watch: {
        "dayList": {
            handler: function (val, oldVal) {
                const self = this;
                val.forEach(function(value){
                    if (value.start && value.end) {
                        const date = value.day;
                        const startHour = value.start.split(":")[0];
                        const startMin = value.start.split(":")[1];

                        const endHour = value.end.split(":")[0];
                        const endMin = value.end.split(":")[1];

                        const start = new Date(date.getYear(), date.getMonth() + 1, date.getDate(), parseInt(startHour,10), parseInt(startMin,10));
                        const end = new Date(date.getYear(), date.getMonth() + 1, date.getDate(), parseInt(endHour,10), parseInt(endMin,10));
                        const workTime = (end.getTime() - start.getTime() - ((1000 * 60 * 60) * value.breakTime)) / 1000 / 60 / 60;
                        value.workTime = workTime;


                    }
                });
                var sum = 0;
                self.dayList.forEach(function(value){
                    if (value.workTime) {
                        sum = sum + value.workTime;
                    }
                });
                console.log("sum". sum);
                self.sumWorkTime = sum;
            },
            deep: true
        }
    }
}