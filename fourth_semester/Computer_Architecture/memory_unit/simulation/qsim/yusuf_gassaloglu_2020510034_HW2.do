onerror {exit -code 1}
vlib work
vlog -work work yusuf_gassaloglu_2020510034_HW2.vo
vlog -work work yusuf_gassaloglu_HW2_vwf.vwf.vt
vsim -novopt -c -t 1ps -L cycloneiii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.yusuf_gassaloglu_2020510034_HW2_vlg_vec_tst -voptargs="+acc"
vcd file -direction yusuf_gassaloglu_2020510034_HW2.msim.vcd
vcd add -internal yusuf_gassaloglu_2020510034_HW2_vlg_vec_tst/*
vcd add -internal yusuf_gassaloglu_2020510034_HW2_vlg_vec_tst/i1/*
run -all
quit -f
